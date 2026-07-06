/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesGerente;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

/**
 * Gráfico de pastel simple usado por el panel MargenUtilidad (HU004 #7) para
 * mostrar, de un vistazo, qué proporción de los ingresos del mes se queda
 * como ganancia neta y qué proporción se destina a costos operativos
 * (pago a guías, transporte y entradas a atracciones).
 *
 * Este componente se agrega en el editor de formularios de NetBeans desde la
 * paleta como "custom component" (clase PanelesGerente.PanelPastel). No
 * requiere ningún subcomponente adicional: todo el dibujo se hace en
 * paintComponent.
 *
 * @author coolg
 */
public class PanelPastel extends JPanel {

    private static final Color COLOR_GANANCIA = new Color(245, 164, 61);
    private static final Color COLOR_COSTOS = new Color(80, 50, 22);
    private static final Color COLOR_SIN_DATOS = new Color(230, 230, 230);
    private static final Color COLOR_TEXTO = new Color(80, 50, 22);

    private double ganancia = 0;
    private double costos = 0;

    public PanelPastel() {
        setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(260, 260));
    }

    /**
     * Actualiza los valores representados en el gráfico y lo repinta.
     *
     * @param ganancia ganancia neta del mes (S/), no puede ser negativa
     * @param costos costos operativos del mes (S/), no puede ser negativa
     */
    public void actualizar(double ganancia, double costos) {
        this.ganancia = Math.max(ganancia, 0);
        this.costos = Math.max(costos, 0);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        int diametro = Math.min(ancho - 20, alto - 90);
        diametro = Math.max(diametro, 60);
        int x = (ancho - diametro) / 2;
        int y = 10;

        double total = ganancia + costos;

        if (total <= 0) {
            g2.setColor(COLOR_SIN_DATOS);
            g2.fill(new Ellipse2D.Double(x, y, diametro, diametro));
            g2.setColor(new Color(140, 140, 140));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            String texto = "Aún sin datos";
            int anchoTexto = g2.getFontMetrics().stringWidth(texto);
            g2.drawString(texto, x + (diametro - anchoTexto) / 2, y + diametro / 2);
        } else {
            double anguloGanancia = (ganancia / total) * 360.0;

            g2.setColor(COLOR_GANANCIA);
            g2.fill(new Arc2D.Double(x, y, diametro, diametro, 90, -anguloGanancia, Arc2D.PIE));

            g2.setColor(COLOR_COSTOS);
            g2.fill(new Arc2D.Double(x, y, diametro, diametro, 90 - anguloGanancia, -(360.0 - anguloGanancia), Arc2D.PIE));

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2f));
            g2.draw(new Ellipse2D.Double(x, y, diametro, diametro));
        }

        int cuadro = 13;
        int leyendaX = 14;
        int leyendaY = y + diametro + 20;

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        double pctGanancia;
        if (total <= 0) {
            pctGanancia = 0; 
        } else {
            pctGanancia = (ganancia / total) * 100;
        }
        
        g2.setColor(COLOR_GANANCIA);
        g2.fillRect(leyendaX, leyendaY, cuadro, cuadro);
        g2.setColor(COLOR_TEXTO);
        g2.drawString(String.format("Ganancia neta (%.1f%%)", pctGanancia), leyendaX + cuadro + 6, leyendaY + cuadro - 1);

        int leyendaY2 = leyendaY + 20;
        double pctCostos;
        if (total <= 0) {
            pctCostos = 0; 
        } else {
            pctCostos = (costos / total) * 100; 
        }
        g2.setColor(COLOR_COSTOS);
        g2.fillRect(leyendaX, leyendaY2, cuadro, cuadro);
        g2.setColor(COLOR_TEXTO);
        g2.drawString(String.format("Costos operativos (%.1f%%)", pctCostos), leyendaX + cuadro + 6, leyendaY2 + cuadro - 1);

        g2.dispose();
    }
}
