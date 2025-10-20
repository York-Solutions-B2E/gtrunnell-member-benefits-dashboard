package com.greggtrunnelldashboard.backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    //using uuidgenerator and TIME it's ordered instead of random UUIDs. Avoids random UUID insert-order issues
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    private String phone;

    @Embedded
    private Address mailingAddress;

//CascadeType.ALL If you save or delete a Member, its associated Enrollments are saved/deleted too.
//orphanRemoval = true If you remove an enrollment from the list in Java and save, JPA deletes it in the DB.
//Keeps things in sync.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    // Prevents infinite JSON recursion when using @Data on bidirectional relationships.
    // Marks this as the parent
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

}
