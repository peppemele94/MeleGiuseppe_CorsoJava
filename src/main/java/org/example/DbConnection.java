package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnection {

    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "root";
    private String db_name = "gestioneordini";
    Connection conn;
    private Statement st;


    public DbConnection() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);

        //apro la connessione
        st = conn.createStatement();
        System.out.println("Db connesso....");
        createDatabase();
        createTables();
    }

    public void createDatabase() throws SQLException {

        String sql = "CREATE DATABASE IF NOT EXISTS " + this.db_name;
        st.executeUpdate(sql);
        System.out.println("DB" + this.db_name + " connesso!! ");
        conn = DriverManager.getConnection(url + db_name, user, pass);
        st = conn.createStatement();

    }

    private void createTables() throws SQLException {

        String clientisql = "CREATE TABLE IF NOT EXISTS Clienti (" +
                "id_cliente INT AUTO_INCREMENT PRIMARY KEY, " +
                "nome VARCHAR(100) NOT NULL, " +
                "cognome VARCHAR(100) NOT NULL, " +
                "email VARCHAR(150) UNIQUE NOT NULL, " +
                "telefono VARCHAR(20) NULL)";
        st.executeUpdate(clientisql);
        System.out.println("Tabella clienti creata");

        String prodottisql = "CREATE TABLE IF NOT EXISTS Prodotti (" +
                "id_prodotto INT AUTO_INCREMENT PRIMARY KEY, " +
                "nome VARCHAR(150) NOT NULL, " +
                "descrizione TEXT NULL, " +
                "prezzo DECIMAL(10,2) NOT NULL, " +
                "quantità_disponibile INT NOT NULL)";
        st.executeUpdate(prodottisql);
        System.out.println("Tabella prodotti creata");

        String ordinisql = "CREATE TABLE IF NOT EXISTS Ordini (" +
                "id_ordine INT AUTO_INCREMENT PRIMARY KEY, " +
                "id_cliente INT NOT NULL, " +
                "data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (id_cliente) REFERENCES Clienti(id_cliente) ON DELETE CASCADE)";
        st.executeUpdate(ordinisql);
        System.out.println("Tabella ordini creata ");

        String dettagliordinesql = "CREATE TABLE IF NOT EXISTS DettagliOrdine (" +
                "id_dettagli_ordini INT AUTO_INCREMENT PRIMARY KEY, " +
                "id_ordine INT, " +
                "id_prodotto INT, " +
                "quantità INT NOT NULL, " +
                "prezzo_unitario DECIMAL(10,2) NOT NULL, " +
                "FOREIGN KEY (id_ordine) REFERENCES Ordini(id_ordine) ON DELETE CASCADE, " +
                "FOREIGN KEY (id_prodotto) REFERENCES Prodotti(id_prodotto) ON DELETE CASCADE)";
        st.executeUpdate(dettagliordinesql);
        System.out.println("TAbella dettagli ordini creata");


    }

    public void inserisciCliente(Cliente c) throws SQLException {

        String sql = "INSERT INTO clienti (nome, cognome, email, telefono) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getCognome());
        ps.setString(3, c.getEmail());
        ps.setString(4, c.getTelefono());
        ps.executeUpdate();
        System.out.println(c.getNome() + " " + c.getCognome() + "salvato nel db");

    }

    public Cliente findCliente(int id) throws SQLException {

        String sql = "SELECT * FROM clienti WHERE id_cliente = " + id;
        ResultSet rs = st.executeQuery(sql);
        Cliente c = null;
        if (rs.next()) {
            int cliente_id = rs.getInt("id_cliente");
            String firstname = rs.getString("nome");
            String lastname = rs.getString("cognome");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            //vado a leggere ogni singolo valore della query e le inserisco in variabili
            c = new Cliente(cliente_id, firstname, lastname, email, telefono);
        }
        return c;
    }

    public void updateCliente(Cliente c) throws SQLException {
        String sql = "UPDATE clienti SET nome = ?, cognome = ?, email = ?, telefono = ? WHERE id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getCognome());
        ps.setString(3, c.getEmail());
        ps.setString(4, c.getTelefono());
        ps.setInt(5, c.getIdCliente());
        ps.executeUpdate();
        System.out.println(c.getNome() + " " + c.getCognome() + " modificato nel db");

    }

    public void removeCliente(Cliente c) throws SQLException {
        String sql = "DELETE FROM clienti WHERE id_cliente = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, c.getIdCliente());
        ps.executeUpdate();
        System.out.println(c.getNome() + " " + c.getCognome() + " eliminato dal db");

    }

    public List<Cliente> findAll() throws SQLException {
        String sql = "SELECT * from clienti";
        ResultSet rs = st.executeQuery(sql);
        List<Cliente> lista = new ArrayList<Cliente>();
        while (rs.next()) {
            int client_id = rs.getInt("id_cliente");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            Cliente c = new Cliente(client_id, nome, cognome, email, telefono);
            lista.add(c);
        }
        return lista;
    }

    public void inserisciProdotto(Prodotto p) throws SQLException {
        String sql = "INSERT INTO prodotti (nome, descrizione, prezzo, quantità_disponibile) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getDescrizione());
        ps.setDouble(3, p.getPrezzo());
        ps.setInt(4, p.getQuantitaDisponibile());
        ps.executeUpdate();
        System.out.println(p.getNome() + "salvato nel db");
    }

    public Prodotto findProdotto(int id) throws SQLException {

        String sql = "SELECT * FROM prodotti WHERE id_prodotto = " + id;
        ResultSet rs = st.executeQuery(sql);
        Prodotto p = null;
        if (rs.next()) {
            int prodotto_id = rs.getInt("id_prodotto");
            String name = rs.getString("nome");
            String descrizione = rs.getString("descrizione");
            double prezzo = rs.getDouble("prezzo");
            int quantita_disponibile = rs.getInt("quantità_disponibile");
            p = new Prodotto(prodotto_id, name, descrizione, prezzo, quantita_disponibile);
        }
        return p;
    }

    public void updateProdotto(Prodotto p) throws SQLException {
        String sql = "UPDATE prodotti SET nome = ?, descrizione = ?, prezzo= ?, quantità_disponibile = ? WHERE id_prodotto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getDescrizione());
        ps.setDouble(3, p.getPrezzo());
        ps.setInt(4, p.getQuantitaDisponibile());
        ps.setInt(5, p.getIdProdotto());
        ps.executeUpdate();
        System.out.println(p.getNome() + " modificato nel db");
    }

    public void removeProdotto(Prodotto p) throws SQLException {
        String sql = "DELETE FROM prodotti WHERE id_prodotto = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, p.getIdProdotto());
        ps.executeUpdate();
        System.out.println(p.getNome()+ " eliminato dal db");

    }

    public List<Prodotto> findAllproduct() throws SQLException {
        String sql = "SELECT * from prodotti";
        ResultSet rs = st.executeQuery(sql);
        List<Prodotto> lista = new ArrayList<Prodotto>();
        while (rs.next()) {
            int prodotto_id = rs.getInt("id_prodotto");
            String nome = rs.getString("nome");
            String descrizione = rs.getString("descrizione");
            double prezzo = rs.getDouble("prezzo");
            int quantitàDisponibile = rs.getInt("quantità_disponibile");
            Prodotto p  = new Prodotto(prodotto_id, nome, descrizione, prezzo, quantitàDisponibile);
            lista.add(p);
        }
        return lista;
    }

    public int inserisciOrdine(int idCliente) throws SQLException {
        String sql = "INSERT INTO ordini (id_cliente) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idCliente);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // Restituisce l'ID dell'ordine appena creato
        }
        return -1; // Indica errore
    }

    public void inserisciDettaglioOrdine(int idOrdine, int idProdotto, int quantita) throws SQLException {
        // Recupera il prezzo del prodotto
        String prezzoSql = "SELECT prezzo, quantità_disponibile FROM prodotti WHERE id_prodotto = ?";
        PreparedStatement psPrezzo = conn.prepareStatement(prezzoSql);
        psPrezzo.setInt(1, idProdotto);
        ResultSet rs = psPrezzo.executeQuery();

        if (rs.next()) {
            double prezzoUnitario = rs.getDouble("prezzo");
            int quantitaDisponibile = rs.getInt("quantità_disponibile");

            if (quantitaDisponibile >= quantita) { // Controlla la disponibilità
                // Inserisce il dettaglio ordine
                String sql = "INSERT INTO dettagliordine (id_ordine, id_prodotto, quantità, prezzo_unitario) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idOrdine);
                ps.setInt(2, idProdotto);
                ps.setInt(3, quantita);
                ps.setDouble(4, prezzoUnitario);
                ps.executeUpdate();

                // Aggiorna la quantità disponibile del prodotto
                String updateSql = "UPDATE prodotti SET quantità_disponibile = quantità_disponibile - ? WHERE id_prodotto = ?";
                PreparedStatement psUpdate = conn.prepareStatement(updateSql);
                psUpdate.setInt(1, quantita);
                psUpdate.setInt(2, idProdotto);
                psUpdate.executeUpdate();

                System.out.println("Prodotto aggiunto all'ordine con successo!");
            } else {
                System.out.println("Quantità non disponibile per il prodotto con ID " + idProdotto);
            }
        } else {
            System.out.println("Prodotto non trovato!");
        }
    }


    public void visualizzaStoricoOrdini() throws SQLException {
        String sql = """
        SELECT o.id_ordine, c.nome, c.cognome, o.data_ordine, 
               p.nome AS prodotto, d.quantità, d.prezzo_unitario 
        FROM ordini o
        JOIN clienti c ON o.id_cliente = c.id_cliente
        JOIN dettagliordine d ON o.id_ordine = d.id_ordine
        JOIN prodotti p ON d.id_prodotto = p.id_prodotto
        ORDER BY o.data_ordine DESC
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("Storico ordini:");
        while (rs.next()) {
            System.out.println("Ordine ID: " + rs.getInt("id_ordine") +
                    " - Cliente: " + rs.getString("nome") + " " + rs.getString("cognome") +
                    " - Data: " + rs.getTimestamp("data_ordine") +
                    " - Prodotto: " + rs.getString("prodotto") +
                    " - Quantità: " + rs.getInt("quantità") +
                    " - Prezzo Unitario: €" + rs.getDouble("prezzo_unitario"));
        }
    }

    public void modificaDettaglioOrdine(int idOrdine, int idProdotto, int nuovaQuantita) throws SQLException {
        // Controlliamo se il prodotto è già presente nell'ordine
        String sqlCheck = "SELECT quantità FROM dettagliordine WHERE id_ordine = ? AND id_prodotto = ?";
        PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
        psCheck.setInt(1, idOrdine);
        psCheck.setInt(2, idProdotto);
        ResultSet rs = psCheck.executeQuery();

        if (rs.next()) {
            // Se il prodotto è già presente, aggiorniamo la quantità
            int quantitaAttuale = rs.getInt("quantità");

            String sqlUpdate = "UPDATE dettagliordine SET quantità = ? WHERE id_ordine = ? AND id_prodotto = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, nuovaQuantita);
            psUpdate.setInt(2, idOrdine);
            psUpdate.setInt(3, idProdotto);
            psUpdate.executeUpdate();

            System.out.println("✅ Quantità aggiornata per il prodotto " + idProdotto + " nell'ordine " + idOrdine);
        } else {
            // Se il prodotto non è presente, lo aggiungiamo all'ordine
            inserisciDettaglioOrdine(idOrdine, idProdotto, nuovaQuantita);
            System.out.println("✅ Prodotto " + idProdotto + " aggiunto all'ordine " + idOrdine);
        }
    }


}
