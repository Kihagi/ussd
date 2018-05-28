package model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Common implements Serializable {
    public static final String ID = "id";
    public static final String CREATED_AT = "createdAt";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, updatable = false, nullable = false)
    public long id = 0;

    @Column(name = "created_at")
    private int createdAt = (int) (System.currentTimeMillis() / 1000);

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

}
