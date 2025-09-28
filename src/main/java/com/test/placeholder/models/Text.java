package com.test.placeholder.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = true) @Entity
//@DiscriminatorValue("t")
public class Text extends Resource{
    String content;
}
