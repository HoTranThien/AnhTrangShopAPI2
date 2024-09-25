package api.anhtrangapiv2.components;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import api.anhtrangapiv2.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter{
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
        )
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        // if(authHeader == null && !authHeader.startsWith("Bearer ")){
        //     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        //     return;
        // }
        // String token = authHeader.substring(7);
        // String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        
        // if(SecurityContextHolder.getContext().getAuthentication() != null){
        //     if(phoneNumber ==null){
        //         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        //         return;
        //     }
        //     User userDetails = (User)userDetailsService.loadUserByUsername(phoneNumber);
        //     if(jwtTokenUtil.validateToken(token, userDetails)){
        //         UsernamePasswordAuthenticationToken authenticationToken
        //         = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        //         authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //     }
        //     else{
        //         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        //         return;
        //     }
        // }

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            
            if(phoneNumber !=null && SecurityContextHolder.getContext().getAuthentication() == null){
                User userDetails = (User)userDetailsService.loadUserByUsername(phoneNumber);
                if(jwtTokenUtil.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }
        filterChain.doFilter(request, response);
        
    }
    

}
