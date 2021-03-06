package converters;

import entities.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessions.ClientFacadeLocal;
import utils.JsfUtil;

@FacesConverter("clientConverter")
public class ClientConverter
        implements Converter {

    @EJB
    private ClientFacadeLocal ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        /* 23 */ if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            /* 24 */ return null;
        }
        /* 26 */ return this.ejbFacade.find(getKey(value));
    }

    Integer getKey(String value) {
        /* 31 */ Integer key = Integer.valueOf(value);
        /* 32 */ return key;
    }

    String getStringKey(Integer value) {
        /* 36 */ return "" + value;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        /* 41 */ if (object == null || (object instanceof String && ((String) object)
                /* 42 */.length() == 0)) {
            /* 43 */ return null;
        }
        /* 45 */ if (object instanceof Client) {
            /* 46 */ Client o = (Client) object;
            /* 47 */ return getStringKey(o.getIdclient());
        }
        /* 49 */ Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Client.class.getName()});
        /* 50 */ return null;
    }
}
