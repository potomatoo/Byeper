export interface InstructorModule {
  playListId: number | null;
  isLoading: boolean;
}

export interface Lecture {
  title: string;
  description: string;
  data: LectureSection;
}

export interface LectureSection {
  title: string;
  videoId: string;
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
