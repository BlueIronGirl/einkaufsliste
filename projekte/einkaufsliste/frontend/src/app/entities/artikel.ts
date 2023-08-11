export interface Artikel {
  id: number;
  name: string;
  anzahl: number;
  gekauft: boolean;
  // TODO
  erstellungsZeitpunkt?: string;
  kaufZeitpunkt?: string;
}
