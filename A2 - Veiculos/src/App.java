public class App {
    public static void main(String[] args) throws Exception {

        Garagem g1 = new Garagem( "Garagem do João", "Jataí");
        g1.salvar();
        
        Categoria c1 = new Categoria("SUV");
        c1.salvar();

        Veiculo v1 = new Veiculo("Polo", 2014, 2014, "azul", "ABC-1234", true);
        v1.salvar();

        Carros carro1 = new Carros(150, 5, "Ar condicionado, Direção hidráulica", v1);
        carro1.salvar();

        Garagem g2 = new Garagem("Garagem do Jonathan", "Jataí");
        g2.salvar();
        g2.pesquisar();
        g2.alterar();
        g2.excluir();
    
        Categoria c2 = new Categoria("Hatch");
        c2.salvar();
        c2.pesquisar();
        c2.alterar();
        c2.excluir();

        Veiculo v2 = new Veiculo("Fusca", 1970, 1970, "verde", "XYZ-5678", false);
        v2.salvar();
        v2.pesquisar();
        v2.alterar();
        v2.excluir();
    
        Carros carro2 = new Carros(130, 4, "Ar condicionado", v2);
        carro2.salvar();
        carro2.pesquisar();
        carro2.alterar();
        carro2.excluir();
    }

}
