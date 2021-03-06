package converters;

import entities.Menu;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessions.MenuFacadeLocal;
import utils.JsfUtil;

@FacesConverter("menuConverter")
public class MenuConverter
        implements Converter {

    @EJB
    private MenuFacadeLocal ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
             return null;
        }
         return this.ejbFacade.find(getKey(value));
    }

    Integer getKey(String value) {
         Integer key = Integer.valueOf(value);
         return key;
    }

    String getStringKey(Integer value) {
         return "" + value;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        /* 40 */ if (object == null || (object instanceof String && ((String) object)
                /* 41 */.length() == 0)) {
            /* 42 */ return null;
        }
        /* 44 */ if (object instanceof Menu) {
            /* 45 */ Menu o = (Menu) object;
            /* 46 */ return getStringKey(o.getIdmenu());
        }
        /* 48 */ Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Menu.class.getName()});
        /* 49 */ return null;
    }
}
