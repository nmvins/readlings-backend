package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noemi on 09.04.2022
 */

@Entity
@Table(name="predefined_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredefinedBook {

    @Id
    private String bookId;
    private String title;
    private String description;
    private String author;
    private String imageUrl;

    @JsonIgnore
    @ManyToMany(mappedBy= "predefinedBooks")
    private List<User> users = new ArrayList<>();

    public PredefinedBook(String bookId, String title, String description, String author, String imageUrl) {
        super();
        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getBookId() {
        return bookId;
    }
}
