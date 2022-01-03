package ru.home.appscheckbot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "apps")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userid", insertable = false, updatable = false)
    private Integer userId;
    private String url;
    private String bundle;
    @Column(name = "installscount")
    private String installsCount;
    private String rating;
    private String status;
    private String title;
    @Column(name = "dateofcreation")
    private Date dateOfCreation;
    @Column(name = "notify_installs_count")
    private Boolean notifyInstallsCount;
    @Column(name = "notify_rating")
    private Boolean notifyRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private BotUser botUser;

    public App() {
        this.status = "moderated";
        this.dateOfCreation = new Date();
        this.notifyInstallsCount = true;
        this.notifyRating = true;
    }

    public String toString() {
        return "App(id=" + this.getId() +
                ", userId=" + this.getUserId() +
                ", url=" + this.getUrl() +
                ", bundle=" + this.getBundle() +
                ", installsCount=" + this.getInstallsCount() +
                ", rating=" + this.getRating() +
                ", status=" + this.getStatus() +
                ", title=" + this.getTitle() +
                ", dateOfCreation=" + this.getDateOfCreation() +
                ", notifyInstallsCount=" + this.getNotifyInstallsCount() +
                ", notifyRating=" + this.getNotifyRating() +")";
    }
}
