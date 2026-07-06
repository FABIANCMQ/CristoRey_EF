/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author coolg
 */
public class ReservaControlador {
    ArrayList<Reserva> reserva = new ArrayList();
    
    public boolean registrarReserva(Pasajero pasajero, PaqueteTuristico paquete, double descuento){
        try{
            if (pasajero == null || paquete == null) {
                return false;
            }
            if (!pasajero.getDocumento().documentoVigente()) {
                return false;
            }
            
            if (!paquete.tieneCupos()) {
                return false;
            }
            if (buscarReservaActiva(pasajero.getDocumento().getNro_doc(), paquete.getCodigo_paquete()) != null) {
                return false;
            }
            
            double precio_final = calcularPrecioFinal(paquete.getCosto(), descuento);
            
            Reserva nueva_reserva = new Reserva(pasajero, paquete, precio_final);
            if (!paquete.agregarPasajero(pasajero)) {
                return false;
            }

            reserva.add(nueva_reserva);

            return true;
            
        }catch(Exception e){
            return false;
        }
    }

    public ArrayList<Reserva> getReserva() {
        return reserva;
    }
    
    public ArrayList<Reserva> reservasEnRango(LocalDate desde, LocalDate hasta){
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (int i = 0; i < reserva.size(); i++) {
        Reserva r = reserva.get(i);
        
        if (r.getEstado().equalsIgnoreCase("Activa")
                && !r.getFecha_reserva().isBefore(desde)
                && !r.getFecha_reserva().isAfter(hasta)) {
            resultado.add(r);
        }
    }
        return resultado;
    }
    
    public double calcularPrecioFinal(double precio, double descuento){
        try {
            if (descuento < 0 || descuento > 100) {
                descuento = 0;
            }
            
            return precio - (precio*descuento / 100);
            
        } catch (Exception e) {
            return precio;
        }
    }
    
    public Reserva buscarReserva(String codigo_reserva){
        for (int i = 0; i < reserva.size(); i++) {
            if (reserva.get(i).getCodigo_reserva().equalsIgnoreCase(codigo_reserva)) {
                return reserva.get(i);
            }
        }
        return null;
    }
    
    public Reserva buscarReservaActiva(String nro_doc, String codigo_paquete){
        for (int i = 0; i < reserva.size(); i++) {
            Reserva reservas = reserva.get(i);
            
            if (reservas.getPasajero().getDocumento().getNro_doc().equalsIgnoreCase(nro_doc)
                    && reservas.getPaquete().getCodigo_paquete().equalsIgnoreCase(codigo_paquete)
                    && reservas.getEstado().equalsIgnoreCase("Activa")) {
                return reservas;
            }
        }
        return null;
    }
    
    public ArrayList<Reserva> buscarReservasActivasPorDocumento(String nro_doc) {
    ArrayList<Reserva> activas = new ArrayList<>();
    for (int i = 0; i < reserva.size(); i++) {
        Reserva r = reserva.get(i);
        if (r.getPasajero().getDocumento().getNro_doc().equalsIgnoreCase(nro_doc)
                && r.getEstado().equalsIgnoreCase("Activa")) {
            activas.add(r); 
        }
    }
    return activas;
    }
    
    
    public ArrayList<Reserva> reservasPendientesAprobacion(){
        ArrayList<Reserva> pendientes = new ArrayList<>();
        for (Reserva r : reserva) {
            if (r.getEstado().equalsIgnoreCase("Activa")
                    && r.getEstado_aprobacion().equalsIgnoreCase("Pendiente")) {
                pendientes.add(r);
            }
        }
        return pendientes;
    }
    
    public boolean aprobarReserva(String codigo_reserva){
        try {
            Reserva reservas = buscarReserva(codigo_reserva);
            if (reservas == null) {
                return false;
            }
            if (!reservas.getEstado().equalsIgnoreCase("Activa")) {;
                return false;
            }
            if (reservas.getEstado_aprobacion().equalsIgnoreCase("Aprobada")) {
                return false;
            }

            Promocion promocion = reservas.getPaquete().promocionVigenteAplicable(reservas.getPasajero());
            double descuento;
            if (promocion != null) {
                descuento = promocion.getPorcentaje_descuento();
            } else {
                descuento = 0;
            }
            double precio_final = calcularPrecioFinal(reservas.getPaquete().getCosto(), descuento);

            reservas.setPrecio_final(precio_final);
            reservas.setEstado_aprobacion("Aprobada");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean rechazarReserva(String codigo_reserva){
        Reserva reservas = buscarReserva(codigo_reserva);
        if (reservas == null) {
            return false;
        }
        if (!reservas.getEstado_aprobacion().equalsIgnoreCase("Pendiente")) {;
            return false;
        }

        boolean cancelada = cancelarReserva(codigo_reserva);
        if (cancelada) {
            reservas.setEstado_aprobacion("Rechazada");
        }
        return cancelada;
    }
    
    public boolean cancelarReserva(String codigo_reserva){
        try {
            Reserva reservas = buscarReserva(codigo_reserva);
            if (reservas == null) {
                return false;
            }
            
            if(reservas.getEstado().equalsIgnoreCase("Cancelada")){
                return false;
            }
            
            if (!reservas.getPaquete().eliminarPasajero(reservas.getPasajero())) {
                return false;
            }

            reservas.setEstado("Cancelada");
            reservas.setFecha_cancelacion(LocalDate.now());

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public ArrayList<Reserva> reservasUltimoMes(){
        ArrayList<Reserva> resultado = new ArrayList<>();
        YearMonth mesActual = YearMonth.now();

        for (int i = 0; i < reserva.size(); i++) {
            Reserva r = reserva.get(i);

            if (r.getEstado().equalsIgnoreCase("Activa")
                    && YearMonth.from(r.getFecha_reserva()).equals(mesActual)) {
                resultado.add(r);
            }
        }
        return resultado;
    }
    
    public double gananciasUltimoMes(){
        double total = 0;
        for (Reserva r : reservasUltimoMes()) {
            total += r.getPrecio_final();
        }
        return total;
    }
    
    public String diaMasConcurridoUltimoMes(){
        ArrayList<Reserva> reservasMes = reservasUltimoMes();

        if (reservasMes.isEmpty()) {
            return "Sin datos";
        }

        Map<DayOfWeek, Integer> conteo = new EnumMap<>(DayOfWeek.class);
        for (Reserva r : reservasMes) {
            DayOfWeek dia = r.getFecha_reserva().getDayOfWeek();
            conteo.put(dia, conteo.getOrDefault(dia, 0) + 1);
        }

        DayOfWeek diaPopular = null;
        int max = -1;
        for (Map.Entry<DayOfWeek, Integer> entry : conteo.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                diaPopular = entry.getKey();
            }
        }

        return traducirDia(diaPopular);
    }

    private String traducirDia(DayOfWeek dia){
        if (dia == null) {
            return "Sin datos";
        }
        switch (dia) {
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miércoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return "-";
        }
    }
    
    public static class RankingPaquete {
        private final PaqueteTuristico paquete;
        private final int cantidadReservas;
        private final double porcentaje;

        public RankingPaquete(PaqueteTuristico paquete, int cantidadReservas, double porcentaje) {
            this.paquete = paquete;
            this.cantidadReservas = cantidadReservas;
            this.porcentaje = porcentaje;
        }

        public PaqueteTuristico getPaquete() {
            return paquete;
        }

        public int getCantidadReservas() {
            return cantidadReservas;
        }

        public double getPorcentaje() {
            return porcentaje;
        }
    }
    
    public ArrayList<RankingPaquete> rankingPopularidadPaquetes(){
        ArrayList<Reserva> reservasMes = reservasUltimoMes();

        LinkedHashMap<String, Integer> conteoPorCodigo = new LinkedHashMap<>();
        HashMap<String, PaqueteTuristico> paquetesPorCodigo = new HashMap<>();

        for (Reserva r : reservasMes) {
            PaqueteTuristico p = r.getPaquete();
            String codigo = p.getCodigo_paquete();

            conteoPorCodigo.put(codigo, conteoPorCodigo.getOrDefault(codigo, 0) + 1);
            paquetesPorCodigo.put(codigo, p);
        }

        int totalReservas = reservasMes.size();
        ArrayList<RankingPaquete> ranking = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : conteoPorCodigo.entrySet()) {
            PaqueteTuristico p = paquetesPorCodigo.get(entry.getKey());
            int cantidad = entry.getValue();
            double porcentaje;
            if (totalReservas == 0) {
                porcentaje = 0; 
            } else {
                porcentaje = (cantidad * 100.0) / totalReservas;
            }

            ranking.add(new RankingPaquete(p, cantidad, porcentaje));
        }

        ranking.sort((a, b) -> Integer.compare(b.getCantidadReservas(), a.getCantidadReservas()));

        return ranking;
    }
    public ArrayList<Reserva> reservasActivas(){
        ArrayList<Reserva> activas = new ArrayList<>();
        for (Reserva r : reserva) {
            if (r.getEstado().equalsIgnoreCase("Activa")) {
                activas.add(r);
            }
        }
        return activas;
    }
    
}
