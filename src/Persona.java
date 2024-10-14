class Persona {
    private String codiceFiscale;
    private String nome;

    public Persona(String codiceFiscale, String nome) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Codice Fiscale: " + codiceFiscale + " - " + "Nome: " + nome;
    }
}
