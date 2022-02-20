package com.jtw.jwtfinal.config;

import com.jtw.jwtfinal.service.CustumUserDetails;
import com.jtw.jwtfinal.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustumUserDetails custumUserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header=request.getHeader("Authorization");
        String jwtToken=null;
        String userName=null;
        if(header != null && header.startsWith("Bearer ")){
            jwtToken=header.substring(7);
            try{
                  userName=jwtUtil.getUserNamefromToken(jwtToken);
            }catch(IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT Token Expired");
            }
        }else{
            System.out.println("Invalid Request Token");
        }
        if (userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userDetails= custumUserDetails.loadUserByUsername(userName);
           if(jwtUtil.validateToken(jwtToken,userDetails)){
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,
                       null,userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
        }
        filterChain.doFilter(request,response);
    }
}
