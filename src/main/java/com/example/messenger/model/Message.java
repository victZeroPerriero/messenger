package com.example.messenger.model;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message too long {more than 2kB}")
    @Column(name = "text")
    private String text;
    @Column(name = "tag")
    private String tag;
    @Column(name = "filename")
    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author=user;
    }
    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }
}
