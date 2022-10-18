import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    private final int tamTabuleiro;
    private final int porquinhos;
    private final int galinhas;
    private int numSolucoes;

    enum AnimalType {
        GALINHA, PORQUINHO
    };

    public Tabuleiro(int porquinhos, int galinhas, int tamTabuleiro) {
        this.porquinhos = porquinhos;
        this.galinhas = galinhas;
        this.tamTabuleiro = tamTabuleiro;
    }

    public void jogar() {

        int[][] tabuleiro = criaTabuleiro();
        int[][] tabuleiroEspacosDisponiveis = criaTabuleiro();
        int espacosLivres = tamTabuleiro * tamTabuleiro;
        TabuleiroEspacoLivre tabuleiroEspacoLivre = new TabuleiroEspacoLivre(tabuleiroEspacosDisponiveis, espacosLivres);
        if (porquinhos == 0 && galinhas == 0) {
            System.out.println("Nenhum animal inserido");
            return;
        }

        if (galinhas > porquinhos) {
            if (!resolvePrimeiraClasse(tabuleiro, 0, 0, 0, 0, this.porquinhos, this.galinhas, AnimalType.GALINHA,
                    false)) {
                System.out.print("Numero solucoes: 0");
                return;
            }
        } else {
            if (!resolvePrimeiraClasse(tabuleiro, 0, 0, 0, 0, this.porquinhos, this.galinhas, AnimalType.PORQUINHO,
                    false)) {
                System.out.print("Numero solucoes: 0");
                return;
            }
        }

        // if (galinhas > 0) {
        // if (!resolve(tabuleiro, 0, 0, 0, 0, this.porquinhos, this.galinhas,
        // AnimalType.GALINHA)) {
        // System.out.print("Numero solucoes: 0");
        // return;
        // }
        // } else {
        // if (!resolve(tabuleiro, 0, 0, 0, 0, this.porquinhos, this.galinhas,
        // AnimalType.PORQUINHO)) {
        // System.out.print("Numero solucoes: 0");
        // return;
        // }
        // }

        System.out.println("Numero solucoes: " + this.numSolucoes);
    }

    private int[][] criaTabuleiro() {
        int[][] tabuleiro = new int[tamTabuleiro][tamTabuleiro];
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                tabuleiro[i][j] = 0;
            }
        }
        return tabuleiro;
    }

    private boolean resolvePrimeiraClasse(int[][] tabuleiro, int linhaPorquinho, int colunaPorquinho, int linhaGalinha,
            int colunaGalinha,
            int numeroPorquinhos, int numeroGalinhas,
            AnimalType animalType, boolean isEmpty) {
        if (numeroGalinhas == 0 && numeroPorquinhos == 0) {
            this.numSolucoes++;
            boardPrinter(tabuleiro);
            return true;
        }
        int linha = 0;
        int coluna = 0;
        if (animalType == AnimalType.PORQUINHO) {
            coluna = colunaPorquinho;
            linha = linhaPorquinho;
        } else if (animalType == AnimalType.GALINHA) {
            linha = linhaGalinha;
            coluna = colunaGalinha;
        }
        if (animalType == AnimalType.PORQUINHO) {
            for (int i = linha; i < tamTabuleiro; i++) {
                for (int j = coluna; j < tamTabuleiro; j++) {
                    if (isEmpty) {
                        if (verificaCasa(tabuleiro, i, j, animalType)) {
                            tabuleiro[i][j] = 2;
                            if (numeroPorquinhos > 0) {
                                resolvePrimeiraClasse(tabuleiro, i, j, linhaGalinha, colunaGalinha,
                                        numeroPorquinhos - 1,
                                        numeroGalinhas,
                                        AnimalType.PORQUINHO, true);
                            }
                            tabuleiro[i][j] = 0;
                        }
                    } else {
                        if (numeroPorquinhos > 0) {
                            tabuleiro[i][j] = 2;
                            if (j < tamTabuleiro - 1) {
                                resolvePrimeiraClasse(tabuleiro, i, j + 1, linhaGalinha, colunaGalinha,
                                        numeroPorquinhos - 1,
                                        numeroGalinhas,
                                        AnimalType.PORQUINHO, false);
                            } else {
                                resolvePrimeiraClasse(tabuleiro, i + 1, j, linhaGalinha, colunaGalinha,
                                        numeroPorquinhos - 1,
                                        numeroGalinhas,
                                        AnimalType.PORQUINHO, false);
                            }
                        } else {
                            resolvePrimeiraClasse(tabuleiro, i, j, linhaGalinha, colunaGalinha,
                                    numeroPorquinhos,
                                    numeroGalinhas,
                                    AnimalType.GALINHA, true);
                        }
                        tabuleiro[i][j] = 0;
                    }
                }
                coluna = 0;
            }
        } else {
            for (int i = linha; i < tamTabuleiro; i++) {
                for (int j = coluna; j < tamTabuleiro; j++) {
                    if (isEmpty) {
                        if (verificaCasa(tabuleiro, i, j, animalType)) {
                            tabuleiro[i][j] = 1;
                            if (numeroGalinhas > 0) {
                                resolvePrimeiraClasse(tabuleiro, linhaPorquinho, colunaPorquinho, i, j,
                                        numeroPorquinhos,
                                        numeroGalinhas - 1,
                                        AnimalType.GALINHA, true);
                            }
                            tabuleiro[i][j] = 0;
                        }
                    } else {
                        if (numeroGalinhas > 0) {
                            tabuleiro[i][j] = 1;
                            if (j < tamTabuleiro - 1) {
                                resolvePrimeiraClasse(tabuleiro, linhaPorquinho, colunaPorquinho, i, j + 1,
                                        numeroPorquinhos,
                                        numeroGalinhas - 1,
                                        AnimalType.GALINHA, false);
                            } else {
                                resolvePrimeiraClasse(tabuleiro, linhaPorquinho, colunaPorquinho, i + 1, j,
                                        numeroPorquinhos,
                                        numeroGalinhas - 1,
                                        AnimalType.GALINHA, false);
                            }
                        } else {
                            resolvePrimeiraClasse(tabuleiro, linhaPorquinho, colunaPorquinho, i, j,
                                    numeroPorquinhos,
                                    numeroGalinhas,
                                    AnimalType.PORQUINHO, true);
                        }
                        tabuleiro[i][j] = 0;
                    }
                }
                coluna = 0;
            }
        }
        return this.numSolucoes > 0;
    }

    private boolean resolve(int[][] tabuleiro, int linhaPorquinho, int colunaPorquinho, int linhaGalinha,
            int colunaGalinha,
            int numeroPorquinhos, int numeroGalinhas,
            AnimalType animalType) {
        if (numeroGalinhas == 0 && numeroPorquinhos == 0) {
            this.numSolucoes++;
            return true;
        }
        int linha = 0;
        int coluna = 0;
        if (animalType == AnimalType.PORQUINHO && numeroPorquinhos > 0) {
            coluna = colunaPorquinho;
            linha = linhaPorquinho;
        } else if (animalType == AnimalType.GALINHA && numeroGalinhas > 0) {
            linha = linhaGalinha;
            coluna = colunaGalinha;
        }

        if (animalType == AnimalType.PORQUINHO) {
            for (int i = linha; i < tamTabuleiro; i++) {
                for (int j = coluna; j < tamTabuleiro; j++) {
                    if (verificaCasa(tabuleiro, i, j, animalType)) {
                        tabuleiro[i][j] = 2;
                        if (numeroGalinhas > 0) {
                            resolve(tabuleiro, i, j, linhaGalinha, colunaGalinha, numeroPorquinhos - 1,
                                    numeroGalinhas,
                                    AnimalType.GALINHA);
                        } else {
                            resolve(tabuleiro, i, j, linhaGalinha, colunaGalinha, numeroPorquinhos - 1,
                                    numeroGalinhas,
                                    AnimalType.PORQUINHO);
                        }
                        tabuleiro[i][j] = 0;
                    }
                    coluna = 0;
                }
            }
        } else {
            for (int i = linha; i < tamTabuleiro; i++) {
                for (int j = coluna; j < tamTabuleiro; j++) {
                    if (verificaCasa(tabuleiro, i, j, animalType)) {
                        tabuleiro[i][j] = 1;
                        if (numeroPorquinhos > 0) {
                            resolve(tabuleiro, linhaPorquinho, colunaPorquinho, i, j, numeroPorquinhos,
                                    numeroGalinhas - 1,
                                    AnimalType.PORQUINHO);
                        } else {
                            resolve(tabuleiro, linhaPorquinho, colunaPorquinho, i, j, numeroPorquinhos,
                                    numeroGalinhas - 1,
                                    AnimalType.GALINHA);
                        }
                        tabuleiro[i][j] = 0;
                    }
                    coluna = 0;
                }
            }
        }
        return this.numSolucoes > 0;
    }

    private boolean verificaCasa(int[][] tabuleiro, int linha, int col, AnimalType tipoAnimal) {
        int numeroChecar = 0;
        if (tipoAnimal == AnimalType.GALINHA) {
            numeroChecar = 2;
        }
        if (tipoAnimal == AnimalType.PORQUINHO) {
            numeroChecar = 1;
        }
        int i, j;

        if (tabuleiro[linha][col] != 0)
            return false;

        for (i = 0; i < tamTabuleiro; i++)
            if (tabuleiro[linha][i] == numeroChecar)
                return false;

        for (i = 0; i < tamTabuleiro; i++)
            if (tabuleiro[i][col] == numeroChecar)
                return false;

        for (i = linha, j = col; i >= 0 && j >= 0; i--, j--)
            if (tabuleiro[i][j] == numeroChecar)
                return false;

        for (i = linha, j = col; j >= 0 && i < tamTabuleiro; i++, j--)
            if (tabuleiro[i][j] == numeroChecar)
                return false;

        for (i = linha, j = col; i < tamTabuleiro && j < tamTabuleiro; i++, j++)
            if (tabuleiro[i][j] == numeroChecar)
                return false;

        for (i = linha, j = col; j < tamTabuleiro && i >= 0; i--, j++)
            if (tabuleiro[i][j] == numeroChecar)
                return false;

        return true;
    }

    private TabuleiroEspacoLivre tabuleiroOcupacao(TabuleiroEspacoLivre tabuleiroEspacos, int linha, int col) {
        int[][] tabuleiro = tabuleiroEspacos.tabuleiroOcupacao;

        for (int i = 0; i < tamTabuleiro; i++) {
            if (tabuleiro[linha][i] == 0) {
                tabuleiroEspacos.espacosLivres--;
            }
            tabuleiro[linha][i] = tabuleiro[linha][i] + 1;
        }

        for (int i = 0; i < tamTabuleiro; i++) {
            if (tabuleiro[i][col] == 0) {
                tabuleiroEspacos.espacosLivres--;
            }
            tabuleiro[i][col] = tabuleiro[i][col] + 1;
        }

        for (int i = linha, j = col; i >= 0 && j >= 0; i--, j--) {
            if (tabuleiro[i][j] == 0) {
                tabuleiroEspacos.espacosLivres--;
            }
            tabuleiro[i][j] = tabuleiro[i][j] + 1;
        }

        for (int i = linha, j = col; j >= 0 && i < tamTabuleiro; i++, j--) {
            if (tabuleiro[i][j] == 0) {
                tabuleiroEspacos.espacosLivres--;
            }
            tabuleiro[i][j] = tabuleiro[i][j] + 1;
        }

        for (int i = linha, j = col; i < tamTabuleiro && j < tamTabuleiro; i++, j++) {
            if (tabuleiro[i][j] == 0) {
                tabuleiroEspacos.espacosLivres--;
            }
            tabuleiro[i][j] = tabuleiro[i][j] + 1;
        }

        for (int i = linha, j = col; j < tamTabuleiro && i >= 0; i--, j++) {
            if (tabuleiro[i][j] == 0) {
                tabuleiroEspacos.espacosLivres--;
            }
            tabuleiro[i][j] = tabuleiro[i][j] + 1;
        }

        return new TabuleiroEspacoLivre(tabuleiro, tabuleiroEspacos.espacosLivres);
    }

    public static void boardPrinter(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2)
                    System.out.print("P" + "  ");
                if (board[i][j] == 1)
                    System.out.print("G" + "  ");
                if (board[i][j] == 0)
                    System.out.print("." + "  ");
            }
            System.out.println("  ");
        }
        System.out.println("  ");
    }

    // private String printaSolucao(int[][] tabuleiro) {
    // StringBuilder mensagem = new StringBuilder();
    // for (int i = 0; i < tamTabuleiro; i++) {
    // for (int j = 0; j < tamTabuleiro; j++)
    // mensagem.append(" " + tabuleiro[i][j] + " ");
    // mensagem.append("\n");
    // }
    // return mensagem.toString();
    // }

}