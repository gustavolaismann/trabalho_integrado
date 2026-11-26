// Produto devolvido pela API.
export interface Produto {
  id: number;
  nome: string;
  cor: string;
  preco: number;
  categoriaId: number;
  categoriaNome: string;
}

// Dados enviados ao cadastrar/editar (POST e PUT).
export interface ProdutoRequest {
  nome: string;
  cor: string;
  preco: number;
  categoriaId: number;
}
