package controllers.mouchard;

import entities.Mouchard;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MouchardController extends AbstractMouchardController implements Serializable{

    @PostConstruct
    private void init() {
        this.mouchard = new Mouchard();
    }
}
