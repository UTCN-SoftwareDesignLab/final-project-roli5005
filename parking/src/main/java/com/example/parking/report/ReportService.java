package com.example.parking.report;

import com.example.parking.reservation.ReservationRepository;
import com.example.parking.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReservationRepository reservationRepository;

    public String getReport(int year,int month) throws IOException {
        List<Reservation> allReservationsList = reservationRepository.findAll();
        List<Reservation> thisMonthsReservationsList = new ArrayList<>();

        for (Reservation r : allReservationsList
        ) {
            if (r.getDate().getMonthValue() == month && r.getDate().getYear()==year)
                thisMonthsReservationsList.add(r);
        }
        if(thisMonthsReservationsList.isEmpty())
            return null;
        return createPDF(thisMonthsReservationsList);

    }

    private void drawTable(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin,
                                 String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = 550f;
        final float tableHeight = rowHeight * rows;
        final float cellMargin=1f;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx,y,nextx,y-tableHeight);
            if(i==3)nextx += 250f;
            else nextx += 75f;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,10);

        float textx = margin+cellMargin;
        float texty = y-15;
        for(int i = 0; i < content.length; i++){
            for(int j = 0 ; j < content[i].length; j++){
                String text = content[i][j];
                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(text);
                contentStream.endText();
                if(j==3)textx += 250f +cellMargin;
                else textx += 75f +cellMargin;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }
    }
    private String createPDF(List<Reservation> reservations) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage( page );

        PDPageContentStream contentStream =
                new PDPageContentStream(doc, page);
        String[][] tableBooks = new String[reservations.size()+2][5];
        String[] columnNames = new String[5];

        columnNames[0] = "ID";
        columnNames[1] = "SPOT";
        columnNames[2] = "USER ID";
        columnNames[3] = "DATE";
        columnNames[4] = "FEE";
        tableBooks[0] = columnNames;
        int i = 0;
        for (Reservation r:reservations
        ) {
            i++;
            String[] row = new String[5];
            row[0] = String.valueOf(r.getId());
            row[1] = String.valueOf(r.getSpotNumber());
            row[2] = String.valueOf(r.getUserId());
            row[3] = r.getDate().toString();
            row[4] = String.valueOf(r.getFee());
            tableBooks[i] = row;

        }
        double sum = reservations.stream().mapToDouble(Reservation::getFee)
                .reduce(0.0,Double::sum);

        String[] row = new String[5];
        row[0] = "TOTAL";
        row[1] = "-";
        row[2] = "-";
        row[3] = "-";
        row[4] = ""+sum;
        tableBooks[reservations.size()+1] = row;

        drawTable(page, contentStream, 700, 20, tableBooks);
        contentStream.close();
        String filename = "MonthlyIncome-"+reservations.get(0).getDate().getMonth()+".pdf";
        doc.save(filename);
        return filename;
    }


}
