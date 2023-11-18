package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.AnneeMois;
import entities.Boutique;
import entities.Client;
import entities.Recette;
import entities.Retrait;
import entities.Versement;
import enumeration.OperationModeType;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintUtils {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_periode_de_" + anneeMois.getIdmois().getNom() + "_" + anneeMois.getIdannee().getIdannee() + "_.pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/hebdomadaire/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 2.7F, 1.2F, 1.2F, 1.2F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add(entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add(entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add(entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add(adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add(adresse2);

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT PERIODIQUE D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Mois de " + anneeMois.getIdmois().getNom() + " " + anneeMois.getIdannee().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Client", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Commissions", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("F. Carnet", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            

            int totalVersement = 0;
            int totalRetrait = 0;
            int sommeCarnet = 0;
            int totalFraisCartnet = 0;

            for (Solde s : soldes) {
                totalVersement += s.getMontantVerse();
                totalRetrait += s.getMontantRetire();
                totalFraisCartnet += s.getCommission();
                sommeCarnet += s.getFraisCarnet();

                table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getFraisCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalVersement), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalRetrait), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalFraisCartnet), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCarnet)), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);
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
            float[] widths = {0.5F, 2.7F, 1.2F, 1.2F, 1.2F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add(entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add(entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add(entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add(adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add(adresse2);

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT JOURNALIER D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Date : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Client", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant versé", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant retiré", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Commissions", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("F. Carnet", true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int totalVersement = 0;
            int totalRetrait = 0;
            int totalFraisCarnet = 0;
            int totalCommission = 0;
            for (Solde s : soldes) {
                totalVersement += s.getMontantVerse();
                totalRetrait += s.getMontantRetire();
                totalCommission += s.getCommission();
                totalFraisCarnet += s.getFraisCarnet();

                table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getFraisCarnet()) + "", true, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            }

            table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalVersement), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalRetrait), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalCommission), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalFraisCarnet), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);
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
            rapport.add(entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add(entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add(entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add(adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add(adresse2);

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RELEVE DES OPERATIONS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Période : " + sdf.format(dateDebut) + " Au " + sdf.format(dateFin), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            Paragraph clientParagraph = new Paragraph("Client : " + client.getNom() + " " + client.getPrenom(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            clientParagraph.setAlignment(1);
            rapport.add(clientParagraph);

            Paragraph clientNumberParagraph = new Paragraph("Numéro de compte : " + client.getNumerocarnet(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            clientNumberParagraph.setAlignment(1);
            rapport.add(clientNumberParagraph);

            rapport.add(new Paragraph(" "));

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

            rapport.add(table);
            rapport.add(new Paragraph(" "));
            rapport.add(table_2);
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_du_" + sdf.format(date) + "_au_" + sdf.format(date1) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/periodique/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 2.7F, 1.2F, 1.2F, 1.2F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add(entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add(entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add(entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add(adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add(adresse2);

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("PERIODE DU " + sdf.format(date) + " AU " + sdf.format(date1), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            Font font_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0);

            table.addCell(createPdfPCell("N°", true, font_12));
            table.addCell(createPdfPCell("Client", true, font_12));
            table.addCell(createPdfPCell("Montant versé", true, font_12));
            table.addCell(createPdfPCell("Montant retiré", true, font_12));
            table.addCell(createPdfPCell("Commissions", true, font_12));
            table.addCell(createPdfPCell("F. Carnet", true, font_12));

            int totalVersement = 0;
            int totalRetrait = 0;
            int totalFraisCarnet = 0;
            int totalCommission = 0;

            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0);
            for (Solde s : soldes) {
                totalVersement += s.getMontantVerse();
                totalRetrait += s.getMontantRetire();
                totalCommission += s.getCommission();
                totalFraisCarnet += s.getFraisCarnet();
              
                table.addCell(createPdfPCell("" + s.getClient().getNumerocarnet(), true, font));
                table.addCell(createPdfPCell("" + s.getClient().getNom() + " " + s.getClient().getPrenom(), false, font));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantVerse()), true, font));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getMontantRetire()), true, font));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getCommission()) + "", true, font));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(s.getFraisCarnet()) + "", true, font));
            }

            table.addCell(createPdfPCell("Totaux", 2, true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalVersement), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalRetrait), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalCommission), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(totalFraisCarnet), true, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    private static List<Recette> extractRecette(int boutiqueId, List<Recette> list) {
        List<Recette> results = new ArrayList<>();
        list.stream().forEach(item -> {
            if (item.getBoutique().getIdBoutique().equals(boutiqueId)) {
                results.add(item);
            }
        });

        list.removeAll(results);
        return results;
    }

    private static Integer sumRecette(List<Recette> list) {
        return list.stream().mapToInt(Recette::getMontant).sum();
    }

    private static void entrePortrait(Document rapport, String titre) throws DocumentException {
        Paragraph entreprise = new Paragraph("ETS FIDO INTER", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
        entreprise.setAlignment(1);
        rapport.add(entreprise);

        Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
        entreprise1.setAlignment(1);
        rapport.add(entreprise1);

        Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
        entreprise2.setAlignment(1);
        rapport.add(entreprise2);

        Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
        entreprise3.setAlignment(1);
        rapport.add(entreprise3);

        Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
        adresse.setAlignment(1);
        rapport.add(adresse);

        Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
        adresse2.setAlignment(1);
        rapport.add(adresse2);

        rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 8F, 0)));

        Paragraph titreParagrap = new Paragraph(titre, new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
        titreParagrap.setAlignment(1);
        rapport.add(titreParagrap);
    }

    public static String printPeriodicRecetteReport(Date startDate, Date endDate, List<Recette> recettes, OperationModeType modeType) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_Recette_du_" + sdf.format(startDate) + "_au_" + sdf.format(endDate) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/periodique/" + fileName));
            rapport.open();

            entrePortrait(rapport, modeType.equals(OperationModeType.RECETTE) ? "RAPPORT D'ACTIVITE - RECETTES DES BOUTIQUES" : "RAPPORT D'ACTIVITE - DEPENSE");

            Paragraph periode = new Paragraph("PERIODE DU " + sdf.format(startDate) + " AU " + sdf.format(endDate), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 8F, 0)));

            Font font_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0);

            switch (modeType) {
                case RECETTE: {

                    float[] widths = {0.4F, 2.5F, 1.0F, 1.0F, 2.0F};
                    PdfPTable table = new PdfPTable(widths);
                    table.setWidthPercentage(100.0F);

                    table.addCell(createPdfPCell("N°", true, font_12));
                    table.addCell(createPdfPCell("Personne", true, font_12));
                    table.addCell(createPdfPCell("Montant versé", true, font_12));
                    table.addCell(createPdfPCell("Date", true, font_12));
                    table.addCell(createPdfPCell("Observation", true, font_12));

                    List<Boutique> boutiques = new ArrayList<>();
                    recettes.stream().forEach((Recette item) -> {
                        if (!boutiques.contains(item.getBoutique())) {
                            boutiques.add(item.getBoutique());
                        }
                    });

                    Map<Integer, List<Recette>> map = new HashMap<>();

                    boutiques.forEach(item -> {
                        map.put(item.getIdBoutique(), extractRecette(item.getIdBoutique(), recettes));
                    });

                    Integer montantTotal = 0;

                    for (Boutique c : boutiques) {
                        if (!map.get(c.getIdBoutique()).isEmpty()) {
                            table.addCell(createPdfPCell(c.getNom() + " / " + c.getCode(), 5, 1, font_12));

                            List<Recette> var = map.get(c.getIdBoutique());

                            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0);

                            var.forEach(item -> {
                                table.addCell(createPdfPCell("" + (var.indexOf(item) + 1), 1, 2, font));
                                table.addCell(createPdfPCell("" + item.getPersonnel().getNom() + " " + item.getPersonnel().getPrenom(), 1, 1, font));
                                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(item.getMontant()), 1, 3, font));
                                table.addCell(createPdfPCell("" + dateFormat.format(item.getDateOperation()), 1, 2, font));
                                table.addCell(createPdfPCell(item.getObservation(), 1, 1, font));
                            });

                            Integer sommeBloc = sumRecette(var);
                            montantTotal += sommeBloc;

                            table.addCell(createPdfPCell("Total -  " + c.getCode(), 2, 1, font_12));
                            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(sommeBloc), 1, 3, font_12));
                            table.addCell(createPdfPCell(" ", 2, 2, font_12));
                        }
                    }

                    Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0);

                    table.addCell(createPdfPCell("Grand Total ", 2, 1, font));
                    table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(montantTotal), 1, 3, font));
                    table.addCell(createPdfPCell(" ", 2, 2, font));

                    rapport.add(table);
                    break;
                }

                case DEPENSE: {

                    float[] widths = {0.6F, 1.0F, 1.0F, 3.0F};
                    PdfPTable table = new PdfPTable(widths);
                    table.setWidthPercentage(100.0F);

                    table.addCell(createPdfPCell("N°", true, font_12));
                    table.addCell(createPdfPCell("Montant versé", true, font_12));
                    table.addCell(createPdfPCell("Date", true, font_12));
                    table.addCell(createPdfPCell("Observation", true, font_12));

                    Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0);
                    recettes.forEach(item -> {
                        table.addCell(createPdfPCell("" + (recettes.indexOf(item) + 1), 1, 2, font));
                        table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(item.getMontant()), 1, 3, font));
                        table.addCell(createPdfPCell("" + dateFormat.format(item.getDateOperation()), 1, 2, font));
                        table.addCell(createPdfPCell(item.getObservation(), 1, 1, font));
                    });

                    table.addCell(createPdfPCell("Total ", 1, 1, font));
                    table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(recettes.stream().mapToInt(Recette::getMontant).sum()), 1, 3, font));
                    table.addCell(createPdfPCell(" ", 2, 2, font));

                    rapport.add(table);
                    break;
                }

            }

            rapport.close();
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Liste_des_client_du_" + sdf.format(new Date()) + ".pdf";
            Document rapport = new Document();

            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/client/" + fileName));
            rapport.open();
            float[] widths = {0.6F, 1.5F, 1.5F, 1.0F, 1.0F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph entreprise = new Paragraph("ETS MOMO", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add(entreprise);

            Paragraph entreprise1 = new Paragraph("Vente en gros et détail des Appareils Electroniques , Accessoires de télephone", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("Informatiques & divers Téléphones - Téléviseurs - Ecran plasma - DVD - Casque", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add(entreprise2);

            Paragraph entreprise3 = new Paragraph(" MP3 - Clés USB , Laptops - Chageurs et Battéries d'origine", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise3.setAlignment(1);
            rapport.add(entreprise3);

            Paragraph adresse = new Paragraph("Avénue Kennedy situé à coté de Score , face SOCAEPE , Tél : 242 80 93 79", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse.setAlignment(1);
            rapport.add(adresse);

            Paragraph adresse2 = new Paragraph(" Marché Salade Tél.: 242 80 93 79 / 650 03 97 89 , N°  Cont : PO48012330267K ", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add(adresse2);

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("LISTE DES CLIENTS 3", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Date : " + sdf.format(new Date()), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            Font font_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0);

            table.addCell(createPdfPCell("N°", true, font_12));
            table.addCell(createPdfPCell("Nom(s)", true, font_12));
            table.addCell(createPdfPCell("Prenom(s)", true, font_12));
            table.addCell(createPdfPCell("N° CNI", true, font_12));
            table.addCell(createPdfPCell("Contact", true, font_12));
            table.addCell(createPdfPCell("Solde", true, font_12));

            int solde = 0;
            for (Client c : clients) {
                solde += c.getSolde();
                table.addCell(createPdfPCell("" + c.getNumerocarnet(), true, font_12));
                table.addCell(createPdfPCell("" + c.getNom(), true, font_12));
                table.addCell(createPdfPCell("" + c.getPrenom(), true, font_12));
                table.addCell(createPdfPCell("" + c.getCni(), true, font_12));
                table.addCell(createPdfPCell("" + c.getContact() + "", true, font_12));
                table.addCell(createPdfPCell(JsfUtil.formaterStringMoney(c.getSolde()), 1, 3, font_12));
            }
            table.addCell(createPdfPCell("Total Solde", 5, 2, font_12));
            
            table.addCell(createPdfPCell(JsfUtil.formaterStringMoney(solde), 1, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }
}
