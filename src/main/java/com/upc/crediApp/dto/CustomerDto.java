package com.upc.crediApp.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CustomerDto {

    public String name;
    public String lastName;
    public String dni;
    public String email;
    public String password;
    public String username;
}
