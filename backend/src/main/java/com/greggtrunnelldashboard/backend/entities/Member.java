package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "members")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
//will have to change nullable to false after testing
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    private String firstName;

    private String lastName;

    private LocalDateTime dateOfBirth;

    private String email;

    private String phone;

    private String mailingAddress;

//CascadeType.ALL If you save or delete a Member, its associated Enrollments are saved/deleted too.
//orphanRemoval = true If you remove an enrollment from the list in Java and save, JPA deletes it in the DB.
//Keeps things in sync.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

}
