export interface PlayListModule {
  AllPlayList: PlayList[] | null;
  PlayList: PlayList | null;
  scrollEnd: boolean;
  PlayListVideos: PlayListVideos[] | null;
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

export interface PlayListVideos {
  video_max_img: number | null;
  video_img: string | null;
  play_complete: number | null;
  playlist_id: number | null;
  video_id: string | null;
  play_note: string | null;
  play_id: number | null;
  play_log: number | null;
  video_title: string | null;
  video_description: string | null;
}
