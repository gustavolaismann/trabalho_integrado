import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { BuscaProdutos } from './pages/busca-produtos/busca-produtos';
import { Login } from './pages/login/login';
import { ProdutoDetalhe } from './pages/produto-detalhe/produto-detalhe';
import { ProdutoForm } from './pages/produto-form/produto-form';
import { ProdutoLista } from './pages/produto-lista/produto-lista';
import { ProdutosPorCategoria } from './pages/produtos-por-categoria/produtos-por-categoria';

// Rotas da aplicação. As protegidas usam o authGuard.
export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'produtos', component: ProdutoLista, canActivate: [authGuard] },
  { path: 'produtos/novo', component: ProdutoForm, canActivate: [authGuard] },
  { path: 'produtos/:id/editar', component: ProdutoForm, canActivate: [authGuard] },
  { path: 'produtos/:id', component: ProdutoDetalhe, canActivate: [authGuard] },
  { path: 'categorias-produtos', component: ProdutosPorCategoria, canActivate: [authGuard] },
  { path: 'buscar', component: BuscaProdutos, canActivate: [authGuard] },
  { path: '', redirectTo: 'produtos', pathMatch: 'full' },
  { path: '**', redirectTo: 'produtos' },
];
