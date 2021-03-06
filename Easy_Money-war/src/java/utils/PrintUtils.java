package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.AnneeMois;
import entities.Client;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintUtils {

    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18.0F, 1);
    public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.RED);
    public static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.BLACK);
    public static Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.BLUE);
    public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1);
    public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 1);

    public static Font setUpFont(float size, int style, BaseColor color) {
        /*  41 */ Font font = new Font();
        /*  42 */ font.setStyle(style);
        /*  43 */ font.setSize(size);
        /*  44 */ font.setColor(color);
        /*  45 */ return font;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, boolean etatHori, Font font) {
        /*  49 */ PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell, font));
        /*  50 */ cell.setColspan(colspan);
        /*  51 */ if (etatHori) {
            /*  52 */ cell.setHorizontalAlignment(1);
        }

        /*  55 */ cell.setVerticalAlignment(1);
        /*  56 */ return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, boolean etatHori) {
        /*  60 */ PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell));
        /*  61 */ cell.setColspan(colspan);
        /*  62 */ if (etatHori) {
            /*  63 */ cell.setHorizontalAlignment(1);
        }

        /*  66 */ cell.setVerticalAlignment(1);
        /*  67 */ return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, boolean etatHori, Font font) {
        /*  71 */ PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell, font));
        /*  72 */ if (etatHori) {
            /*  73 */ cell.setHorizontalAlignment(1);
        }

        /*  76 */ cell.setVerticalAlignment(1);
        /*  77 */ return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, boolean etatHori) {
        /*  81 */ PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell));
        /*  82 */ if (etatHori) {
            /*  83 */ cell.setHorizontalAlignment(1);
        }

        /*  86 */ cell.setVerticalAlignment(1);
        /*  87 */ return cell;
    }

    public static String printWeeklyReport(List<Solde> soldes, AnneeMois anneeMois) {
        /*  91 */ String fileName = "";
        try {
            /*  93 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /*  94 */ fileName = "Rapport_periode_de_" + anneeMois.getIdmois().getNom() + "_" + anneeMois.getIdannee().getIdannee() + "_.pdf";
            /*  95 */ Document rapport = new Document();
            /*  96 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/hebdomadaire/" + fileName));
            /*  97 */ rapport.open();
            /*  98 */ float[] widths = {0.4F, 2.5F, 1.0F, 1.0F, 1.2F, 1.0F, 1.2F};
            /*  99 */ PdfPTable table = new PdfPTable(widths);
            /* 100 */ table.setWidthPercentage(100.0F);

            /* 102 */ Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            /* 103 */ entreprise.setAlignment(1);
            /* 104 */ rapport.add((Element) entreprise);

            /* 106 */ Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 107 */ entreprise1.setAlignment(1);
            /* 108 */ rapport.add((Element) entreprise1);

            /* 110 */ Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 111 */ entreprise2.setAlignment(1);
            /* 112 */ rapport.add((Element) entreprise2);

            /* 114 */ Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 115 */ entreprise3.setAlignment(1);
            /* 116 */ rapport.add((Element) entreprise3);

            /* 118 */ Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 119 */ adresse.setAlignment(1);
            /* 120 */ rapport.add((Element) adresse);

            /* 122 */ Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 123 */ adresse2.setAlignment(1);
            /* 124 */ rapport.add((Element) adresse2);

            /* 126 */ rapport.add((Element) new Paragraph(" "));

            /* 128 */ Paragraph titre = new Paragraph("RAPPORT PERIODIQUE D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 129 */ titre.setAlignment(1);
            /* 130 */ rapport.add((Element) titre);

            /* 132 */ Paragraph periode = new Paragraph("Mois de " + anneeMois.getIdmois().getNom() + " " + anneeMois.getIdannee().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            /* 133 */ periode.setAlignment(1);
            /* 134 */ rapport.add((Element) periode);

            /* 136 */ rapport.add((Element) new Paragraph(" "));

            /* 138 */ table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 139 */ table.addCell(createPdfPCell("Client", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 140 */ table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 141 */ table.addCell(createPdfPCell("Montant retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 142 */ table.addCell(createPdfPCell("Commissions", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 143 */ table.addCell(createPdfPCell("F. Carnet", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 144 */ table.addCell(createPdfPCell("Solde", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            /* 146 */ int sommeVerse = 0;
            /* 147 */ int sommeRetire = 0;
            /* 148 */ int sommeCarnet = 0;
            /* 149 */ int sommeCommission = 0;
            /* 150 */ int solde = 0;
            /* 151 */ for (Solde s : soldes) {
                /* 152 */ sommeVerse += s.getMontantVerse().intValue();
                /* 153 */ sommeRetire += s.getMontantRetire().intValue();
                /* 154 */ sommeCommission += s.getCommission().intValue();
                /* 155 */ sommeCarnet += s.getCarnet().intValue();
                /* 156 */ solde += s.getClient().getSolde().intValue();
                /* 157 */ table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 158 */ table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 159 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 160 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 161 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 162 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 163 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getClient().getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            /* 166 */ table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 167 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 168 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 169 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 170 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 171 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            /* 173 */ rapport.add((Element) table);
            /* 174 */ rapport.close();
        } /* 176 */ catch (DocumentException ex) {
            /* 177 */ Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
            /* 178 */        } catch (FileNotFoundException ex) {
            /* 179 */ Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        /* 181 */ return fileName;
    }

    public static String printDailylyReport(Date date, List<Solde> soldes) {
        /* 185 */ String fileName = "";
        try {
            /* 187 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 188 */ fileName = "Rapport_du_" + sdf.format(date) + ".pdf";
            /* 189 */ Document rapport = new Document();
            /* 190 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/journalier/" + fileName));
            /* 191 */ rapport.open();
            /* 192 */ float[] widths = {0.4F, 2.5F, 1.0F, 1.0F, 1.2F, 1.0F, 1.2F};
            /* 193 */ PdfPTable table = new PdfPTable(widths);
            /* 194 */ table.setWidthPercentage(100.0F);

            /* 196 */ Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            /* 197 */ entreprise.setAlignment(1);
            /* 198 */ rapport.add((Element) entreprise);

            /* 200 */ Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 201 */ entreprise1.setAlignment(1);
            /* 202 */ rapport.add((Element) entreprise1);

            /* 204 */ Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 205 */ entreprise2.setAlignment(1);
            /* 206 */ rapport.add((Element) entreprise2);

            /* 208 */ Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 209 */ entreprise3.setAlignment(1);
            /* 210 */ rapport.add((Element) entreprise3);

            /* 212 */ Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 213 */ adresse.setAlignment(1);
            /* 214 */ rapport.add((Element) adresse);

            /* 216 */ Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 217 */ adresse2.setAlignment(1);
            /* 218 */ rapport.add((Element) adresse2);

            /* 220 */ rapport.add((Element) new Paragraph(" "));

            /* 222 */ Paragraph titre = new Paragraph("RAPPORT JOURNALIER D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 223 */ titre.setAlignment(1);
            /* 224 */ rapport.add((Element) titre);

            /* 226 */ Paragraph periode = new Paragraph("Date : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            /* 227 */ periode.setAlignment(1);
            /* 228 */ rapport.add((Element) periode);

            /* 230 */ rapport.add((Element) new Paragraph(" "));

            /* 232 */ table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 233 */ table.addCell(createPdfPCell("Client", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 234 */ table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 235 */ table.addCell(createPdfPCell("Montant retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 236 */ table.addCell(createPdfPCell("Commissions", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 237 */ table.addCell(createPdfPCell("F. Carnet", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 238 */ table.addCell(createPdfPCell("Solde", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            /* 240 */ int sommeVerse = 0;
            /* 241 */ int sommeRetire = 0;
            /* 242 */ int sommeCarnet = 0;
            /* 243 */ int sommeCommission = 0;
            /* 244 */ int solde = 0;
            /* 245 */ for (Solde s : soldes) {
                /* 246 */ sommeVerse += s.getMontantVerse().intValue();
                /* 247 */ sommeRetire += s.getMontantRetire().intValue();
                /* 248 */ sommeCommission += s.getCommission().intValue();
                /* 249 */ sommeCarnet += s.getCarnet().intValue();
                /* 250 */ solde += s.getClient().getSolde().intValue();
                /* 251 */ table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 252 */ table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 253 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 254 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 255 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 256 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 257 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getClient().getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            /* 260 */ table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 261 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 262 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 263 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 264 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 265 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            /* 267 */ rapport.add((Element) table);
            /* 268 */ rapport.close();
        } /* 270 */ catch (DocumentException ex) {
            /* 271 */ Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
            /* 272 */        } catch (FileNotFoundException ex) {
            /* 273 */ Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        /* 275 */ return fileName;
    }

    public static String printDailylyReport(Date date, Date date1, List<Solde> soldes) {
        /* 279 */ String fileName = "";
        try {
            /* 281 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 282 */ fileName = "Rapport_du_" + sdf.format(date) + "_au_" + sdf.format(date1) + ".pdf";
            /* 283 */ Document rapport = new Document();
            /* 284 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/periodique/" + fileName));
            /* 285 */ rapport.open();
            /* 286 */ float[] widths = {0.4F, 2.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.2F};
            /* 287 */ PdfPTable table = new PdfPTable(widths);
            /* 288 */ table.setWidthPercentage(100.0F);

            /* 290 */ Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            /* 291 */ entreprise.setAlignment(1);
            /* 292 */ rapport.add((Element) entreprise);

            /* 294 */ Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 295 */ entreprise1.setAlignment(1);
            /* 296 */ rapport.add((Element) entreprise1);

            /* 298 */ Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 299 */ entreprise2.setAlignment(1);
            /* 300 */ rapport.add((Element) entreprise2);

            /* 302 */ Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 303 */ entreprise3.setAlignment(1);
            /* 304 */ rapport.add((Element) entreprise3);

            /* 306 */ Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 307 */ adresse.setAlignment(1);
            /* 308 */ rapport.add((Element) adresse);

            /* 310 */ Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 311 */ adresse2.setAlignment(1);
            /* 312 */ rapport.add((Element) adresse2);

            /* 314 */ rapport.add((Element) new Paragraph(" "));

            /* 316 */ Paragraph titre = new Paragraph("RAPPORT D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 317 */ titre.setAlignment(1);
            /* 318 */ rapport.add((Element) titre);

            /* 320 */ Paragraph periode = new Paragraph("PERIODE DU " + sdf.format(date) + " AU " + sdf.format(date1), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            /* 321 */ periode.setAlignment(1);
            /* 322 */ rapport.add((Element) periode);

            /* 324 */ rapport.add((Element) new Paragraph(" "));

            /* 326 */ table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 327 */ table.addCell(createPdfPCell("Client", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 328 */ table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 329 */ table.addCell(createPdfPCell("Montant retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 330 */ table.addCell(createPdfPCell("Commissions", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 331 */ table.addCell(createPdfPCell("F. Carnet", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 332 */ table.addCell(createPdfPCell("Solde", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            /* 334 */ int sommeVerse = 0;
            /* 335 */ int sommeRetire = 0;
            /* 336 */ int sommeCarnet = 0;
            /* 337 */ int sommeCommission = 0;
            /* 338 */ int solde = 0;
            /* 339 */ for (Solde s : soldes) {
                /* 340 */ sommeVerse += s.getMontantVerse().intValue();
                /* 341 */ sommeRetire += s.getMontantRetire().intValue();
                /* 342 */ sommeCommission += s.getCommission().intValue();
                /* 343 */ sommeCarnet += s.getCarnet().intValue();
                /* 344 */ solde += s.getClient().getSolde().intValue();
                /* 345 */ table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 346 */ table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 347 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 348 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 349 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 350 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 351 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getClient().getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            /* 354 */ table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 355 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 356 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 357 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 358 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 359 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            /* 361 */ rapport.add((Element) table);
            /* 362 */ rapport.close();
        } /* 364 */ catch (DocumentException ex) {
            /* 365 */ Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
            /* 366 */        } catch (FileNotFoundException ex) {
            /* 367 */ Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        /* 369 */ return fileName;
    }

    public static String printCustomerList(List<Client> clients) {
        /* 373 */ String fileName = "";
        try {
            /* 375 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 376 */ fileName = "Liste_des_client_du_" + sdf.format(new Date()) + ".pdf";
            /* 377 */ Document rapport = new Document();

            /* 379 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/client/" + fileName));
            /* 380 */ rapport.open();
            /* 381 */ float[] widths = {0.6F, 1.5F, 1.5F, 1.0F, 1.0F, 1.0F};
            /* 382 */ PdfPTable table = new PdfPTable(widths);
            /* 383 */ table.setWidthPercentage(100.0F);

            /* 385 */ Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            /* 386 */ entreprise.setAlignment(1);
            /* 387 */ rapport.add((Element) entreprise);

            /* 389 */ Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 390 */ entreprise1.setAlignment(1);
            /* 391 */ rapport.add((Element) entreprise1);

            /* 393 */ Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 394 */ entreprise2.setAlignment(1);
            /* 395 */ rapport.add((Element) entreprise2);

            /* 397 */ Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 398 */ entreprise3.setAlignment(1);
            /* 399 */ rapport.add((Element) entreprise3);

            /* 401 */ Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 402 */ adresse.setAlignment(1);
            /* 403 */ rapport.add((Element) adresse);

            /* 405 */ Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 406 */ adresse2.setAlignment(1);
            /* 407 */ rapport.add((Element) adresse2);

            /* 409 */ rapport.add((Element) new Paragraph(" "));

            /* 411 */ Paragraph titre = new Paragraph("LISTE DES CLIENTS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 412 */ titre.setAlignment(1);
            /* 413 */ rapport.add((Element) titre);

            /* 415 */ Paragraph periode = new Paragraph("Date : " + sdf.format(new Date()), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            /* 416 */ periode.setAlignment(1);
            /* 417 */ rapport.add((Element) periode);

            /* 419 */ rapport.add((Element) new Paragraph(" "));

            /* 421 */ table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 422 */ table.addCell(createPdfPCell("Nom(s)", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 423 */ table.addCell(createPdfPCell("Prenom(s)", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 424 */ table.addCell(createPdfPCell("N° CNI", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 425 */ table.addCell(createPdfPCell("CONTACT", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 426 */ table.addCell(createPdfPCell("SOLDE", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            /* 428 */ int solde = 0;
            /* 429 */ for (Client c : clients) {
                /* 430 */ solde += c.getSolde().intValue();
                /* 431 */ table.addCell(createPdfPCell("" + c.getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 432 */ table.addCell(createPdfPCell("" + c.getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 433 */ table.addCell(createPdfPCell("" + c.getPrenom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 434 */ table.addCell(createPdfPCell("" + c.getCni(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 435 */ table.addCell(createPdfPCell("" + c.getContact() + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 436 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(c.getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            }
            /* 438 */ table.addCell(createPdfPCell("Liquidité en caisse : ", 4, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 439 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add((Element) table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }
}
