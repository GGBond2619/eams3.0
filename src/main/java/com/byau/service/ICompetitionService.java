package com.byau.service;

import com.byau.domain.Competition;
import java.util.List;

public interface ICompetitionService {
  List<Competition> findAll();
  
  List<Competition> findCompetitionsByPage(int paramInt1, int paramInt2);
  
  Competition findCompetitionByCid(Integer paramInteger);
  
  List<Competition> findCompetitionByEle(String paramString1, String paramString2);
  
  List<Competition> findCompetitionByCname(String paramString);
  
  int findCount();
  
  void insertCompetition(Competition paramCompetition);
  
  void deleteCompetition(Integer paramInteger);
  
  void updateCompetition(Integer paramInteger, Competition paramCompetition);
}


/* Location:              C:\Users\11871\Desktop\eams.war!\WEB-INF\classes\com\byau\service\ICompetitionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */