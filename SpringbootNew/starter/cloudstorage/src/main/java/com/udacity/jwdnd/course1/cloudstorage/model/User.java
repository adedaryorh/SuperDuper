package com.udacity.jwdnd.course1.cloudstorage.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
   private Integer userId;
   private String username;
   private String salt;
   private String password;
   private String firstName;
   private String lastName;
}
