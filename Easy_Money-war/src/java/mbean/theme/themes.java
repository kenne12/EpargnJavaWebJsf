package mbean.theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "themes")
@SessionScoped
public class themes implements Serializable {

    List<String> themes;

    @PostConstruct
    public void init() {
        /* 26 */ this.themes = new ArrayList<>();
        /* 27 */ this.themes.add("afterdark");
        /* 28 */ this.themes.add("afternoon");
        /* 29 */ this.themes.add("afterwork");
        /* 30 */ this.themes.add("aristo");
        /* 31 */ this.themes.add("black-tie");
        /* 32 */ this.themes.add("blitzer");
        /* 33 */ this.themes.add("bluesky");
        /* 34 */ this.themes.add("bootstrap");
        /* 35 */ this.themes.add("casablanca");
        /* 36 */ this.themes.add("cupertino");
        /* 37 */ this.themes.add("cruze");
        /* 38 */ this.themes.add("dark-hive");
        /* 39 */ this.themes.add("delta");
        /* 40 */ this.themes.add("dot-luv");
        /* 41 */ this.themes.add("eggplant");
        /* 42 */ this.themes.add("excite-bike");
        /* 43 */ this.themes.add("flick");
        /* 44 */ this.themes.add("glass-x");
        /* 45 */ this.themes.add("home");
        /* 46 */ this.themes.add("hot-sneaks");
        /* 47 */ this.themes.add("humanity");
        /* 48 */ this.themes.add("le-frog");
        /* 49 */ this.themes.add("midnight");
        /* 50 */ this.themes.add("mint-choc");
        /* 51 */ this.themes.add("overcast");
        /* 52 */ this.themes.add("pepper-grinder");
        /* 53 */ this.themes.add("rocket");
        /* 54 */ this.themes.add("sam");
        /* 55 */ this.themes.add("smoothness");
        /* 56 */ this.themes.add("south-street");
        /* 57 */ this.themes.add("start");
        /* 58 */ this.themes.add("sunny");
        /* 59 */ this.themes.add("swanky-purse");
        /* 60 */ this.themes.add("trontastic");
        /* 61 */ this.themes.add("ui-darkness");
        /* 62 */ this.themes.add("ui-lightness");
        /* 63 */ this.themes.add("vader");
    }

    public List<String> getThemes() {
        /* 67 */ return this.themes;
    }

    public void setThemes(List<String> list) {
        /* 71 */ this.themes = list;
    }
}
