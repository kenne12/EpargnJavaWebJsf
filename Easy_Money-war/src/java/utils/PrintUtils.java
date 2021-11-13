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
import entities.Retrait;
import entities.Versement;
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
        Font font = new Font();
        font.setStyle(style);
        font.setSize(size);
        font.setColor(color);
        return font;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, boolean etatHori, Font font) {
        PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell, font));
        cell.setColspan(colspan);
        if (etatHori) {
            cell.setHorizontalAlignment(1);
        }

        cell.setVerticalAlignment(1);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, int position, Font font) {
        PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell, font));
        cell.setColspan(colspan);
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, boolean etatHori) {
        PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell));
        cell.setColspan(colspan);
        if (etatHori) {
            cell.setHorizontalAlignment(1);
        }

        cell.setVerticalAlignment(1);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, boolean etatHori, Font font) {
        PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell, font));
        if (etatHori) {
            cell.setHorizontalAlignment(1);
        }

        cell.setVerticalAlignment(1);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, boolean etatHori) {
        PdfPCell cell = new PdfPCell((Phrase) new Paragraph(sCell));
        if (etatHori) {
            cell.setHorizontalAlignment(1);
        }

        cell.setVerticalAlignment(1);
        return cell;
    }

    public static String printWeeklyReport(List<Solde> soldes, AnneeMois anneeMois) {
        String fileName = "";
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
            for (Solde s : soldes) {
                /* 152 */ sommeVerse += s.getMontantVerse();
                /* 153 */ sommeRetire += s.getMontantRetire();
                /* 154 */ sommeCommission += s.getCommission();
                /* 155 */ sommeCarnet += s.getCarnet();
                /* 156 */ solde += s.getClient().getSolde();
                /* 157 */ table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 158 */ table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 159 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 160 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 161 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 162 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 163 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getClient().getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            /* 166 */ table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 167 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 168 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 169 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 170 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 171 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add((Element) table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printDailylyReport(Date date, List<Solde> soldes) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_du_" + sdf.format(date) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/journalier/" + fileName));
            rapport.open();
            float[] widths = {0.4F, 2.5F, 1.0F, 1.0F, 1.2F, 1.0F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add((Element) entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add((Element) entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add((Element) entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add((Element) entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add((Element) adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add((Element) adresse2);

            rapport.add((Element) new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT JOURNALIER D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add((Element) titre);

            Paragraph periode = new Paragraph("Date : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add((Element) periode);

            rapport.add((Element) new Paragraph(" "));

            table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Client", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Commissions", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("F. Carnet", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Solde", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int sommeVerse = 0;
            int sommeRetire = 0;
            int sommeCarnet = 0;
            int sommeCommission = 0;
            int solde = 0;
            for (Solde s : soldes) {
                sommeVerse += s.getMontantVerse();
                sommeRetire += s.getMontantRetire();
                sommeCommission += s.getCommission();
                sommeCarnet += s.getCarnet();
                solde += s.getClient().getSolde();
                table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getClient().getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add((Element) table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printAccountMvt(Client client, Date dateDebut, Date dateFin, List<Versement> versement, List<Retrait> retrait) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Releve_de_compte_de_" + client.getNom() + "_" + client.getNumerocarnet() + "_du" + sdf.format(dateDebut) + "_au_" + sdf.format(dateFin) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/journalier/" + fileName));
            rapport.open();

            Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add((Element) entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add((Element) entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add((Element) entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add((Element) entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add((Element) adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add((Element) adresse2);

            rapport.add((Element) new Paragraph(" "));

            Paragraph titre = new Paragraph("RELEVE DES OPERATIONS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add((Element) titre);

            Paragraph periode = new Paragraph("Période : " + sdf.format(dateDebut) + " Au " + sdf.format(dateFin), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add((Element) periode);

            Paragraph clientParagraph = new Paragraph("Client : " + client.getNom() + " " + client.getPrenom(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            clientParagraph.setAlignment(1);
            rapport.add((Element) clientParagraph);

            Paragraph clientNumberParagraph = new Paragraph("Numéro de compte : " + client.getNumerocarnet(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            clientNumberParagraph.setAlignment(1);
            rapport.add((Element) clientNumberParagraph);

            rapport.add((Element) new Paragraph(" "));

            float[] widths = {0.4F, 2.5F, 1.0F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            table.addCell(createPdfPCell("LISTE DES VERSEMENTS", 4, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Date", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Solde", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int sommeVerse = 0;
            int i = 0;
            for (Versement v : versement) {
                sommeVerse += v.getMontant();
                table.addCell(createPdfPCell("" + (i + 1), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + sdf.format(v.getDateOperation()), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(v.getMontant()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(v.getSolde()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                i++;
            }

            table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" / ", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            float[] widths_page2 = {0.4F, 2.5F, 1.0F, 1.0F, 1F};
            PdfPTable table_2 = new PdfPTable(widths_page2);
            table_2.setWidthPercentage(100.0F);

            table_2.addCell(createPdfPCell("LISTE DES RETRAITS", 5, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table_2.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table_2.addCell(createPdfPCell("Date", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table_2.addCell(createPdfPCell("Montant Retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table_2.addCell(createPdfPCell("Commission", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table_2.addCell(createPdfPCell("Solde", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int sommeRetire = 0;
            int sommeCommission = 0;
            i = 0;
            for (Retrait r : retrait) {
                sommeRetire += r.getMontant();
                sommeCommission += r.getCommission();
                table_2.addCell(createPdfPCell("" + (i + 1), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table_2.addCell(createPdfPCell("" + sdf.format(r.getDateOperation()), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table_2.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(r.getMontant()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table_2.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(r.getCommission()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table_2.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(r.getSolde()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                i++;
            }

            table_2.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table_2.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table_2.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney((sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table_2.addCell(createPdfPCell(" / ", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add((Element) table);
            rapport.add((Element) new Paragraph(" "));
            rapport.add((Element) table_2);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printDailylyReport(Date date, Date date1, List<Solde> soldes) {
        String fileName = "";
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
            for (Solde s : soldes) {
                /* 340 */ sommeVerse += s.getMontantVerse();
                /* 341 */ sommeRetire += s.getMontantRetire();
                /* 342 */ sommeCommission += s.getCommission();
                /* 343 */ sommeCarnet += s.getCarnet();
                /* 344 */ solde += s.getClient().getSolde();
                /* 345 */ table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 346 */ table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 347 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 348 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 349 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 350 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                /* 351 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getClient().getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            /* 354 */ table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 355 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeVerse)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 356 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeRetire)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 357 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCommission)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 358 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 359 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            /* 361 */ rapport.add((Element) table);
            /* 362 */ rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printCustomerList(List<Client> clients) {
        String fileName = "";
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

            int solde = 0;
            for (Client c : clients) {
                /* 430 */ solde += c.getSolde();
                /* 431 */ table.addCell(createPdfPCell("" + c.getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 432 */ table.addCell(createPdfPCell("" + c.getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 433 */ table.addCell(createPdfPCell("" + c.getPrenom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 434 */ table.addCell(createPdfPCell("" + c.getCni(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 435 */ table.addCell(createPdfPCell("" + c.getContact() + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                /* 436 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(c.getSolde()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            }
            table.addCell(createPdfPCell("Liquidité en caisse : ", 4, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((solde)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

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
