export interface PlayListModule {
  AllPlayList: PlayList[] | null;
  PlayList: PlayList | null;
  scrollEnd: boolean;
}

export interface PlayList {
  playlistId: number | null;
  playlistTitle: string | null;
  playlistDescription: string | null;
  userId: string | null;
  playlistType: number | null;
  playlistCategory: number | null;
  playlistLevel: number | null;
  playlistImg: string | null;
}
