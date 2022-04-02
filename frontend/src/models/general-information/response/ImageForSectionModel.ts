export interface ImageForSectionModel {
  imageForSectionId: number;
  url: string;
  fileName: string;
  section: Section;
}

export interface Section {
  sectionId: number;
  name: string;
}
