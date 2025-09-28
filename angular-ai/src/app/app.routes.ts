import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: 'simple-chat', 
        loadComponent: () => import('./chat/simple-chat/simple-chat').then(c => c.SimpleChat),
    },
    { path: '**', redirectTo: 'simple-chat' }
];
