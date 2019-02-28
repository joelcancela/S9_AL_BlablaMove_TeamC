import {NbMenuItem} from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'fa fa-home',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'Administration',
    icon: 'fa fa-wrench',
    link: '/pages/administration',
  },
  {
    title: 'Marketing',
    icon: 'fa fa-eur',
    children: [
      {
        title: 'Most active cities',
        link: '/pages/marketing-most-active-cities',
      },
      {
        title: 'Last 24h created routes',
        link: '/pages/marketing-created-routes',
      },
      {
        title: 'Last 24h canceled routes',
        link: '/pages/marketing-canceled-routes',
      },
      {
        title: 'Last 24h delivered items',
        link: '/pages/marketing-delivered-items',
      },
      {
        title: 'Last 24h delivery issues',
        link: '/pages/marketing-delivery-issues',
      },
    ],
  },
];
