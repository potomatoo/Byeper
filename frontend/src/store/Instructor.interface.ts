export interface InstructorModule {
  lectures: Lecture[] | null;
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
