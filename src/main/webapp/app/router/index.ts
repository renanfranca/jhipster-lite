import { GithubConfigurationVue } from '@/module/primary/github-configuration';
import { LandscapeVue } from '@/module/primary/landscape';
import { ModulesVue } from '@/module/primary/modules-patch';
import { createRouter, createWebHistory } from 'vue-router';

export const routes = [
  {
    path: '/',
    redirect: { name: 'landscape' },
  },
  {
    path: '/patches',
    name: 'module-patches',
    component: ModulesVue,
  },
  {
    path: '/landscape',
    name: 'landscape',
    component: LandscapeVue,
  },
  {
    path: '/github-configuration',
    name: 'github-configuration',
    component: GithubConfigurationVue,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
