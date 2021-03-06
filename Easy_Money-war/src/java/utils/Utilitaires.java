package utils;

import entities.Mouchard;
import entities.Utilisateur;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sessions.MouchardFacadeLocal;

public class Utilitaires {

    private static final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public static final String path = servletContext.getRealPath("");
    public static final String chemin = servletContext.getContextPath();

    public static void saveOperation(MouchardFacadeLocal mouchardFacadeLocal, String action, Utilisateur utilisateur) {
        try {
            /*  40 */ Mouchard traceur = new Mouchard();
            /*  41 */ traceur.setIdmouchard(mouchardFacadeLocal.nextVal());
            /*  42 */ traceur.setAction(action);
            /*  43 */ traceur.setIdutilisateur(utilisateur);
            /*  44 */ traceur.setDate(new Date());
            /*  45 */ traceur.setHeure(new Date());
            /*  46 */ mouchardFacadeLocal.create(traceur);
            /*  47 */        } catch (Exception e) {
            /*  48 */ e.printStackTrace();
        }
    }

    public static String getExtension(String nomFichier) {
        /*  53 */ int taille = nomFichier.length();
        /*  54 */ String extension = "";
        /*  55 */ for (int i = 0; i < taille; i++) {
            /*  56 */ if (nomFichier.charAt(i) == '.') {
                /*  57 */ extension = "";
            } else {

                /*  60 */ extension = extension + nomFichier.charAt(i);
            }
            /*  62 */        }
        return extension;
    }

    public static boolean estExtensionImage(String extension) {
        /*  66 */ if (extension == null || extension.equals("")) {
            /*  67 */ return false;
        }
        /*  69 */ String ext = extension.toUpperCase();
        /*  70 */ if (ext.equals("JPG")) {
            /*  71 */ return true;
        }
        /*  73 */ if (ext.equals("JPEG")) {
            /*  74 */ return true;
        }
        /*  76 */ if (ext.equals("GIF")) {
            /*  77 */ return true;
        }
        /*  79 */ if (ext.equals("PNG")) {
            /*  80 */ return true;
        }
        /*  82 */ if (ext.equals("BMP")) {
            /*  83 */ return true;
        }
        /*  85 */ return false;
    }

    public static boolean estFichierImage(String nom) {
        /*  89 */ String extension = getExtension(nom);
        /*  90 */ if (extension == null || extension.equals("")) {
            /*  91 */ return false;
        }
        /*  93 */ String ext = extension.toUpperCase();
        /*  94 */ if (ext.equals("JPG")) {
            /*  95 */ return true;
        }
        /*  97 */ if (ext.equals("JPEG")) {
            /*  98 */ return true;
        }
        /* 100 */ if (ext.equals("GIF")) {
            /* 101 */ return true;
        }
        /* 103 */ if (ext.equals("PNG")) {
            /* 104 */ return true;
        }
        /* 106 */ if (ext.equals("BMP")) {
            /* 107 */ return true;
        }
        /* 109 */ return false;
    }

    public static boolean handleFileUpload(FileUploadEvent event, String absoluteSavePath) {
        try {
            /* 115 */ OutputStream saveFile = new FileOutputStream(new File(absoluteSavePath));
            /* 116 */ InputStream in = event.getFile().getInputstream();
            /* 117 */ byte[] buff = new byte[8];
            int n;
            /* 119 */ while ((n = in.read(buff)) >= 0) {
                /* 120 */ saveFile.write(buff);
                /* 121 */ buff = new byte[8];
            }
            /* 123 */        } catch (IOException ex) {
            /* 124 */ FacesMessage message = new FacesMessage("Error", "Error While uploading " + event.getFile().getFileName());
            /* 125 */ FacesContext.getCurrentInstance().addMessage(null, message);
            /* 126 */ Logger.getLogger(utils.Utilitaires.class.getName()).log(Level.SEVERE, (String) null, ex);
            /* 127 */ return false;
        }
        /* 129 */ return true;
    }

    public static boolean handleFileUpload(UploadedFile file, String absoluteSavePath) {
        try {
            /* 136 */ OutputStream saveFile = new FileOutputStream(new File(absoluteSavePath));
            /* 137 */ InputStream in = file.getInputstream();
            /* 138 */ byte[] buff = new byte[8];
            int n;
            /* 140 */ while ((n = in.read(buff)) >= 0) {
                /* 141 */ saveFile.write(buff);
                /* 142 */ buff = new byte[8];
            }
            /* 144 */        } catch (IOException ex) {
            /* 145 */ FacesMessage message = new FacesMessage("Error", "Error While uploading " + file.getFileName());
            /* 146 */ FacesContext.getCurrentInstance().addMessage(null, message);
            /* 147 */ Logger.getLogger(utils.Utilitaires.class.getName()).log(Level.SEVERE, (String) null, ex);
            /* 148 */ return false;
        }
        /* 150 */ return true;
    }

    public static boolean CopierFichier(File Source, File Destination) {
        /* 154 */ boolean resultat = false;

        /* 156 */ FileInputStream filesource = null;
        /* 157 */ FileOutputStream fileDestination = null;
        try {
            /* 159 */ filesource = new FileInputStream(Source);
            /* 160 */ fileDestination = new FileOutputStream(Destination);
            /* 161 */ byte[] buffer = new byte[1000];
            int nblecture;
            /* 163 */ while ((nblecture = filesource.read(buffer)) != -1) {
                /* 164 */ fileDestination.write(buffer, 0, nblecture);
                /* 165 */ buffer = new byte[8];
            }
            /* 167 */ resultat = true;
            /* 168 */        } catch (FileNotFoundException nf) {
            /* 169 */ nf.printStackTrace();
            /* 170 */        } catch (IOException io) {
            /* 171 */ io.printStackTrace();
        } finally {
            try {
                /* 174 */ filesource.close();
                /* 175 */            } catch (Exception e) {
                /* 176 */ e.printStackTrace();
            }
            try {
                /* 179 */ fileDestination.close();
                /* 180 */            } catch (Exception e) {
                /* 181 */ e.printStackTrace();
            }
        }
        /* 184 */ return resultat;
    }
}
