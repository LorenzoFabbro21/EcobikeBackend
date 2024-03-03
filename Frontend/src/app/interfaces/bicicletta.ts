import { Taglia } from "../enum/tagliaEnum"
export interface Bicicletta {

  id?: number,
  model?: string,
  brand?: string,
  color?: string,
  size?: Taglia,
  type?: string,
  measure?: string,
  img?: string

}