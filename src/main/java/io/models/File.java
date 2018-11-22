package io.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "files")
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "extension", nullable = false)
    private String extension;

//    @Temporal(TemporalType.DATE)
//    @Column(name = "date_created")
    private Date dateCreated;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "date_updated")
    private Date dateUpdated;

    public File () {}

    public File (String path, String name, String extension) {
        this.path = path;
        this.name = name;
        this.extension = extension;
    }

    public long getId () {
        return id;
    }

    public String getPath () {
        return path;
    }

    public String getExtension () {
        return extension;
    }

    public Date getDateCreated () {
        return dateCreated;
    }

    public Date getDateUpdated () {
        return dateUpdated;
    }

    public String getName () {
        return name;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof File)) {
            return false;
        }
        File file = (File) o;
        return id == file.id &&
                Objects.equals(path, file.path) &&
                Objects.equals(name, file.name) &&
                Objects.equals(extension, file.extension) &&
                Objects.equals(dateCreated, file.dateCreated) &&
                Objects.equals(dateUpdated, file.dateUpdated);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, path, name, extension, dateCreated, dateUpdated);
    }

    @Override
    public String toString () {
        return "File{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
