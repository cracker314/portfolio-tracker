package org.rakana.portfoliotracker.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Investor extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max= 10, message = "Name cannot be more than 10 characters long")
    private String name;

    public Investor(String name) {
        this.name = name;
    }

    public Investor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
