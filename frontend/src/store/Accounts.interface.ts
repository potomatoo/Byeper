export interface AccountsModule {
  user: User | null;
  token: string | null;
  isSidebar: boolean | null;
}

export interface User {
  userId: string | null;
  userPassword: string | null;
  userType: number | null;
  userName: string | null;
  channelId: string | null;
}
