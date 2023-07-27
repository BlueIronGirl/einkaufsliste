import {Kategorie} from "./kategorie";

export interface Artikel {
  id: number;
  name: string;
  kategorie: Kategorie;
  anzahl: number;
  gekauft: boolean;
  // TODO
  // erstellungsZeitpunkt: string;
  // kaufZeitpunkt: string;
}
