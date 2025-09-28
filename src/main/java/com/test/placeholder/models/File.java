package com.test.placeholder.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder @AllArgsConstructor @NoArgsConstructor @Entity @EqualsAndHashCode(callSuper = true)
//@DiscriminatorValue("r")
public class File extends Resource{
    private String type;
}
