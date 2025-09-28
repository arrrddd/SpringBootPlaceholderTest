package com.test.placeholder.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder @Entity //@DiscriminatorValue("v")//for single table
//@PrimaryKeyJoinColumn(name = "video_id") FOR JOINED
public class Video extends Resource{
    int length;
}
