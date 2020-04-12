package com.example.demo3.Controller;

import com.example.demo3.dto.AccessTokenDTO;
import com.example.demo3.dto.GithubUser;
import com.example.demo3.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String secret;

    @Value("${github.redirect.uri}")
    private String uri;




    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        /*---------------------代替-------------*/
        accessTokenDTO.setClient_id(clientId);
       // accessTokenDTO.setClient_id("7865437a79e0848919e6");

        accessTokenDTO.setRedirect_uri(uri);
        //accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");

        accessTokenDTO.setClient_secret(secret);
     // accessTokenDTO.setClient_secret("fe0065c02734637f6d1ad7a841da7160e7003fc0");
        /*---------------------代替-------------*/
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
         GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }


}
