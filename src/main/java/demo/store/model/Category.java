package demo.store.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
