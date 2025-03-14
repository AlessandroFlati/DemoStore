package demo.store.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Category {
    @Id
    private Long id;
    @Column(unique=true)
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
