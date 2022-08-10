package com.lee.demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.demo.repositories.UserRepository;
import com.lee.demo.repositories.RoleRepository;
import com.lee.demo.config.JwtUtils;
import com.lee.demo.models.Role;
import com.lee.demo.models.User;
import com.lee.demo.payload.request.LoginRequest;
import com.lee.demo.payload.request.SignupRequest;
import com.lee.demo.payload.response.ErrorsResponse;
import com.lee.demo.payload.response.JwtResponse;
import com.lee.demo.payload.response.MessageResponse;
import com.lee.demo.services.UserDetailsImpl;
import com.lee.demo.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
    UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;

	private JwtResponse generateTokenResponse (String email, String password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtResponse(jwt, 
								new User(userDetails.getId(), userDetails.getEmail()));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		return ResponseEntity.ok(this.generateTokenResponse(loginRequest.getEmail(), loginRequest.getPassword()));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		List <String> errors = new ArrayList<String>();
		if ( userRepository.existsByEmail(signUpRequest.getEmail()) ) {
			errors.add("Email unavailable");
		}
		
		if ( signUpRequest.getPassword() == "" || signUpRequest.getPassword() == null) {
			errors.add("Password Required");
		}
		else if ( ! signUpRequest.getPassword().equals(signUpRequest.getConfirm_password()) ) {
			errors.add("Passwords must match");
		}

		if ( errors.size() > 0 ) {
			return ResponseEntity.status(422).body(new ErrorsResponse(errors));
		}
		// Create new user's account
		User user = new User(signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: user Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName("ROLE_ADMIN") // originally ERole.ROLE_ADMIN
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName("ROLE_MODERATOR")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName("ROLE_USER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/current-user")
	public ResponseEntity<?> currentUser(
		Principal principal
		) {
			
			User user = userService.findByEmail(principal.getName());
			String jwt = jwtUtils.generateJwtToken(principal.getName());	
			
			return ResponseEntity.ok(new JwtResponse(jwt, new User(user.getId(), user.getEmail())));
	}
}
