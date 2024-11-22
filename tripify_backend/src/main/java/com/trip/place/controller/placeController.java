package com.trip.place.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.common.vo.ItemAndUserId;
import com.trip.place.service.placeService;
import com.trip.place.util.PageNavigation;
import com.trip.place.vo.PlaceComment;
import com.trip.place.vo.PlaceCommentRequest;
import com.trip.place.vo.PlaceCommentWithUserName;
import com.trip.place.vo.PlaceWithLikeStatus;
import com.trip.place.vo.Places;
import com.trip.place.vo.Search;
import com.trip.place.vo.SidoGugunCode;
import com.trip.place.vo.Type;
import com.trip.user.vo.User;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:80"}, allowCredentials = "true")
@RestController
public class placeController {
	
	@Autowired
	placeService service;
	
	@GetMapping("/place/sido")
	public ResponseEntity<List<SidoGugunCode>> sido() throws Exception {
		return new ResponseEntity<List<SidoGugunCode>>(service.getSido(), HttpStatus.OK);
	}

	@GetMapping("/place/gugun")
	public ResponseEntity<List<SidoGugunCode>> gugun(
			@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCode>>(service.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/place/type")
	public ResponseEntity<List<Type>> type() throws Exception{
		return new ResponseEntity<List<Type>>(service.typeSelect(), HttpStatus.OK);
	}
	
	@PostMapping("/place/search")
	public Map<String, Object> search(@RequestBody Search search) throws Exception{
		//시도, 구군, 관광지타입, 키워드를 받아 해당 내용으로 DB select 하기
		Map<String, Object> map = new HashMap<>();
		
		List<Places> list = service.listAttr(search); //6개씩 페이지 번호에 맞춰서 DB 데이터 가져오기
		PageNavigation pageNavigation = service.makePageNavigation(search); //페이지 번호 만들기
		map.put("attrList", list); //가져온 관광지정보 리스트
		map.put("navigation", pageNavigation);
		map.put("pgno", search.getPgno()); //페이지 넘버
		map.put("sido", search.getSido()); //시도
		map.put("gugun", search.getGugun()); //구군
		map.put("type", search.getType()); //어트랙션 종류
		map.put("word", search.getWord()); //검색어
		return map;
	}
	
	// 여행지 가져오기
    @GetMapping("/place/{placeId}")
    public ResponseEntity<Places> getPlaceById(@PathVariable Integer placeId) {
        Places place = service.getPlaceById(placeId);
        return ResponseEntity.ok(place);
    }
	
	// 여행지 댓글 가져오기
    @GetMapping("/place/{placeId}/comments")
    public ResponseEntity<List<PlaceCommentWithUserName>> getCommentsByPlaceId(@PathVariable Integer placeId) {
        List<PlaceCommentWithUserName> comments = service.getCommentsByPlaceId(placeId);
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping("place/{placeId}/comments")
    public ResponseEntity<String> addComment(
            @PathVariable Integer placeId,
            @RequestBody PlaceCommentRequest commentRequest) {

        // `commentRequest`로 userId와 content를 받음
        PlaceComment placeComment = new PlaceComment();
        placeComment.setPlaceId(placeId);
        placeComment.setUserId(commentRequest.getUserId());
        placeComment.setContent(commentRequest.getContent());

        // Service를 통해 댓글 저장
        Integer isAdded = service.addPlaceComment(placeComment);

        if (isAdded>0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 추가에 실패했습니다.");
        }
    }
    
    @DeleteMapping("place/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        try {
            service.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("댓글 삭제 중 오류가 발생했습니다.");
        }
    }
    
    @PostMapping("/place/search/{userId}")
	public Map<String, Object> searchWithLikeStatus(@RequestBody Search search, @PathVariable int userId) throws Exception{
		//시도, 구군, 관광지타입, 키워드를 받아 해당 내용으로 DB select 하기
		Map<String, Object> map = new HashMap<>();
		
		List<PlaceWithLikeStatus> list = service.getPlaceListWithLikeStatus(search, userId); //6개씩 페이지 번호에 맞춰서 DB 데이터 가져오기
		PageNavigation pageNavigation = service.makePageNavigation(search); //페이지 번호 만들기
		map.put("attrList", list); //가져온 관광지정보 리스트
		map.put("navigation", pageNavigation);
		map.put("pgno", search.getPgno()); //페이지 넘버
		map.put("sido", search.getSido()); //시도
		map.put("gugun", search.getGugun()); //구군
		map.put("type", search.getType()); //어트랙션 종류
		map.put("word", search.getWord()); //검색어
		return map;
	}
    
 // 좋아요 추가
    @PostMapping("/place/like")
    public ResponseEntity<Void> addLike(@RequestBody ItemAndUserId request) {
        service.addLike(request.getUserId(), request.getPlaceId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 좋아요 삭제
    @DeleteMapping("/place/like")
    public ResponseEntity<Void> removeLike(@RequestBody ItemAndUserId request) {
        service.removeLike(request.getUserId(), request.getPlaceId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 좋아요 개수 조회
    @GetMapping("/place/likecount/{placeId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable int placeId) {
        int likeCount = service.getLikeCount(placeId);
        return ResponseEntity.ok(likeCount);
    }

    // 좋아요한 장소 리스트 조회
    @GetMapping("place/likedplaces")
    public ResponseEntity<List<PlaceWithLikeStatus>> getLikedPlaces(@RequestBody User userId) {
        List<PlaceWithLikeStatus> likedPlaces = service.getLikedPlaces((int) userId.getUserId());
        return ResponseEntity.ok(likedPlaces);
    }
}
