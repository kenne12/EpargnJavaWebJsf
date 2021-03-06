package utils;

import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;

public class JsfUtil {

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        /*  14 */ String msg = ex.getLocalizedMessage();
        /*  15 */ if (msg != null && msg.length() > 0) {
            /*  16 */ addErrorMessage(msg);
        } else {
            /*  18 */ addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        /*  23 */ for (String message : messages) {
            /*  24 */ addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        /*  29 */ FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        /*  30 */ FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        /*  31 */ FacesContext.getCurrentInstance().validationFailed();
    }

    public static void addSuccessMessage(String msg) {
        /*  36 */ FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        /*  37 */ FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addWarningMessage(String msg) {
        /*  41 */ FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        /*  42 */ FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addFatalErrorMessage(String msg) {
        /*  46 */ FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg);
        /*  47 */ FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static Throwable getRootCause(Throwable cause) {
        /*  51 */ if (cause != null) {
            /*  52 */ Throwable source = cause.getCause();
            /*  53 */ if (source != null) {
                /*  54 */ return getRootCause(source);
            }
            /*  56 */ return cause;
        }

        /*  59 */ return null;
    }

    public static boolean isValidationFailed() {
        /*  63 */ return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static boolean isDummySelectItem(UIComponent component, String value) {
        /*  67 */ for (UIComponent children : component.getChildren()) {
            /*  68 */ if (children instanceof UISelectItem) {
                /*  69 */ UISelectItem item = (UISelectItem) children;
                /*  70 */ if (item.getItemValue() == null && item.getItemLabel().equals(value)) {
                    /*  71 */ return true;
                }
                break;
            }
        }
        /*  76 */ return false;
    }

    public static String getComponentMessages(String clientComponent, String defaultMessage) {
        /*  80 */ FacesContext fc = FacesContext.getCurrentInstance();
        /*  81 */ UIComponent component = UIComponent.getCurrentComponent(fc).findComponent(clientComponent);
        /*  82 */ if (component instanceof UIInput) {
            /*  83 */ UIInput inputComponent = (UIInput) component;
            /*  84 */ if (inputComponent.isValid()) {
                /*  85 */ return defaultMessage;
            }
            /*  87 */ Iterator<FacesMessage> iter = fc.getMessages(inputComponent.getClientId());
            /*  88 */ if (iter.hasNext()) {
                /*  89 */ return ((FacesMessage) iter.next()).getDetail();
            }
        }

        /*  93 */ return "";
    }

    public static String formaterStringMoney(Long valeur) {
        /*  97 */ String chaine = Long.toString(valeur.longValue());
        /*  98 */ if (chaine == null) {
            /*  99 */ return null;
        }
        /* 101 */ int taille = chaine.length(), j = taille;
        /* 102 */ String result = "";
        /* 103 */ int i = 0;
        /* 104 */ while (i < taille) {
            /* 105 */ result = result + chaine.charAt(i);
            /* 106 */ i++;
            /* 107 */ j--;
            /* 108 */ if (j > 0 && j % 3 == 0) {
                /* 109 */ result = result + ' ';
            }
        }

        /* 113 */ return result;
    }

    public static String formaterStringMoney(Integer valeur) {
        /* 117 */ String chaine = Integer.toString(valeur.intValue());
        /* 118 */ if (chaine == null) {
            /* 119 */ return null;
        }
        /* 121 */ int taille = chaine.length(), j = taille;
        /* 122 */ String result = "";
        /* 123 */ int i = 0;
        /* 124 */ while (i < taille) {
            /* 125 */ result = result + chaine.charAt(i);
            /* 126 */ i++;
            /* 127 */ j--;
            /* 128 */ if (j > 0 && j % 3 == 0) {
                /* 129 */ result = result + ' ';
            }
        }

        /* 133 */ return result;
    }

    public static String formaterStringMoney(String valeur) {
        /* 137 */ String chaine = valeur;
        /* 138 */ if (chaine == null) {
            /* 139 */ return null;
        }
        /* 141 */ int taille = chaine.length(), j = taille;
        /* 142 */ String result = "";
        /* 143 */ int i = 0;
        /* 144 */ while (i < taille) {
            /* 145 */ result = result + chaine.charAt(i);
            /* 146 */ i++;
            /* 147 */ j--;
            /* 148 */ if (j > 0 && j % 3 == 0) {
                /* 149 */ result = result + ' ';
            }
        }

        /* 153 */ return result;
    }

    public static String formaterStringMoney(Double val) {
        /* 157 */ String pEntiere = partieEntiere(val);
        /* 158 */ String pDec = partieDecimale(val);
        /* 159 */ String chaine = pEntiere;
        /* 160 */ int taille = chaine.length(), j = taille;
        /* 161 */ String result = "";
        /* 162 */ int i = 0;
        /* 163 */ while (i < taille) {
            /* 164 */ result = result + chaine.charAt(i);
            /* 165 */ i++;
            /* 166 */ j--;
            /* 167 */ if (j > 0 && j % 3 == 0) {
                /* 168 */ result = result + ' ';
            }
        }
        /* 171 */ if (pDec != null) {
            /* 172 */ result = result + "." + pDec;
        }
        /* 174 */ return result;
    }

    private static String partieDecimale(Double nombre) {
        /* 178 */ return partieDecimale(nombre.toString());
    }

    private static String partieDecimale(String nombre) {
        /* 182 */ String result = "";
        /* 183 */ int taille = nombre.length();
        /* 184 */ boolean copie = false;
        /* 185 */ for (int i = 0; i < taille; i++) {
            /* 186 */ if (copie) {
                /* 187 */ result = result + nombre.charAt(i);
                /* 188 */            } else if (nombre.charAt(i) == '.') {
                /* 189 */ copie = true;
            }
        }
        /* 192 */ if (result.equals("0")) {
            /* 193 */ return null;
        }
        /* 195 */ return result;
    }

    private static String partieEntiere(Double nombre) {
        /* 199 */ Integer tmp = Integer.valueOf(nombre.intValue());
        /* 200 */ return tmp.toString();
    }
}
