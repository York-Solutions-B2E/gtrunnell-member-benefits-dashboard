## 🧩 what this file _is_

`@Configuration public class SecurityConfig {`

- `@Configuration` tells Spring Boot this class provides beans for the application context — basically, setup instructions.
    
- it’s a **Java-based security configuration**, replacing the old `WebSecurityConfigurerAdapter` (which was deprecated).
    

---

## 🧱 define a `SecurityFilterChain`

`@Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {`

- a **SecurityFilterChain** is the ordered list of filters that Spring Security runs on every HTTP request.
    
- defining this bean tells Spring exactly how to secure each request — which endpoints require login, how tokens are checked, etc.
    

---

## 🚫 disable CSRF (Cross-Site Request Forgery)

`http.csrf(AbstractHttpConfigurer::disable)`

- CSRF protection is useful for traditional form logins with cookies.
    
- since your frontend and backend communicate through **JWT tokens** in headers (stateless), csrf isn’t needed.
    
- disabling it avoids “403 Forbidden” errors when sending requests from the browser.
    

---

## 🌐 enable CORS (Cross-Origin Resource Sharing)

`.cors(Customizer.withDefaults())`

- CORS allows requests from your React app (`http://localhost:5173`) to reach your Spring API (`http://localhost:8080`).
    
- `Customizer.withDefaults()` tells Spring to use your **CorsConfig.java** bean (which you created separately).
    
- this is crucial so that the browser doesn’t block preflight `OPTIONS` requests.
    

---

## 🔐 authorize requests

`.authorizeHttpRequests(auth -> auth     .requestMatchers("/api/public/**").permitAll()     .anyRequest().authenticated() )`

- this section decides _who can access what_.
    
- anything under `/api/public/**` → **no login required**.
    
- everything else → must be **authenticated** (i.e., must have a valid JWT token).
    

so:

- `GET /api/public/hello` works for anyone.
    
- `GET /api/private/hello` requires a verified token from google.
    

---

## 🪪 set up as a Resource Server (JWT-based auth)

`.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))`

this single line turns your backend into a **JWT verifier**:

1. spring looks at your `application.properties` for
    
    `spring.security.oauth2.resourceserver.jwt.issuer-uri=https://accounts.google.com`
    
2. it downloads google’s public signing keys.
    
3. when a request has a header `Authorization: Bearer <token>`, it:
    
    - verifies the signature,
        
    - checks expiration, issuer, and audience,
        
    - and, if valid, creates a `JwtAuthenticationToken` in the security context.
        
4. your controller can then use:
    
    `@AuthenticationPrincipal Jwt jwt`
    
    to access the token claims like email or sub.
    

no database check, no session — all stateless, verified cryptographically.

---

## ✅ build it

`return http.build();`

this finalizes the configuration and returns the built `SecurityFilterChain` to Spring Boot to register.

---

## 🧠 mental model

**every incoming request** passes through this flow:

```
React (token in header)
   ↓
Spring Security FilterChain
   ↓
JWT Validator (talks to Google)
   ↓
Authorization rules (public or private?)
   ↓
Controller (if allowed)

```

---

## 💡 summary

| Section                                  | Purpose                                   |
| ---------------------------------------- | ----------------------------------------- |
| `@Configuration`                         | declares a config class                   |
| `SecurityFilterChain`                    | main Spring Security setup                |
| `.csrf(AbstractHttpConfigurer::disable)` | disables unnecessary CSRF                 |
| `.cors(Customizer.withDefaults())`       | enables browser access from your frontend |
| `.authorizeHttpRequests(...)`            | controls who can call which routes        |
| `.oauth2ResourceServer(...jwt...)`       | enables JWT verification using Google     |
| `return http.build()`                    | activates it all                          |