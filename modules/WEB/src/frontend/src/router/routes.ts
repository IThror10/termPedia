import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router';
import HomePage from "@/pages/HomePage.vue";

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/pages/HomePage.vue')
    },
    {
        path: '/terms',
        name: 'Terms',
        component: () => import('@/pages/TermSearchPage.vue')
    },
    {
        path: '/terms/:tid',
        name: 'TermPage',
        component: () => import('@/pages/TermPage.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/pages/SignIn.vue')
    },
    {
        path: '/profile',
        name: 'profile',
        component: () => import('@/pages/ProfilePage.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/pages/SignUp.vue')
    },
    {
        path: '/:catchAll(.*)',
        name: 'PageNotFound',
        component: () => import('../pages/ErrorNotFound.vue')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router