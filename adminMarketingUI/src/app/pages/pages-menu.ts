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
        title: 'Stats',
        link: '/pages/stats',
      },
      {
        title: 'Most active cities',
        link: '/pages/marketing',
      },
      {
        title: 'User connections',
        link: '/pages/user-stats',
      },
      {
        title: 'Items moved',
        link: '/pages/items-stats',
      },
      {
        title: 'Routes stats',
        link: '/pages/routes-stats',
      },
      {
        title: 'Issues stats',
        link: '/pages/issues-stats',
      },
    ],
  },
];
