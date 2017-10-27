<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navber.css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="_pageUri" value="${pageContext.request.contextPath}/board/list/${pageUri}" />
<nav class="navbar navbar-default">
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

	<c:choose>
		<c:when test="${!empty sessionScope.user}">
			<span class="navbar-login"><a href="${contextPath}/login/logout.do">Logout</a></span>
		</c:when>
		<c:otherwise>
			<span class="navbar-login"><a href="${contextPath}/login/signoutform.do">Sign Out</a></span>
			<span class="navbar-login"><a href="${contextPath}/login/signinform.do">Sign In</a></span>
		</c:otherwise>
	</c:choose>
	
	<div class="container">
		<div class="row">
			<div class="collapse navbar-collapse" id="main-menu">
				<ul class="nav navbar-nav">
					<c:if test="${!empty applicationScope.cateList}">
						<c:forEach var="i" items="${applicationScope.cateList}">
							<li class="dropdown fadeInDown animated d3 navbarmenu">
								<c:choose>
									<c:when test="${i.group_num == 0}">
										<a href="${contextPath}/${i.uriName}.do" class="trigger right-caret">
											<c:out value="${i.name}"></c:out>
										</a>
										<c:set var="tmp" value="${i.uriName}.do" />
										<c:if test="${tmp == pageUri}">
											<c:set var="_pageUri" value="${pageContext.request.contextPath}/${pageUri}" />
										</c:if>
									</c:when>
									<c:otherwise>
										<%-- href="${contextPath}/board/list/${i.uriName}.do"  --%>
										<a class="trigger right-caret">
											<c:out value="${i.name}"></c:out>
										</a>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${!empty applicationScope.boardList.get(i.group_num)}">
									<ul class="firstlevel dropdown-menu sub-menu" style="display: none;">
										<c:forEach var="l" items="${applicationScope.boardList.get(i.group_num)}">
											<li>
												<a href="${contextPath}/board/list/${l.uriName}.do">
													<c:out value="${l.name}"/>
												</a>
											</li>
										</c:forEach>
									</ul>
								</c:if>
							</li>
						</c:forEach>
					</c:if>
					<li class="navbarmenu"><a href="${pageContext.request.contextPath}/quiz.do">Sketch quiz</a></li>
       			</ul>
			</div>
			
			<div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
		</div>
	</div>
</nav>
<script type="text/javascript">
	$(function() {//closest("li.navbarmenu").
		$(".navbar.navbar-default ul.nav.navbar-nav > li").removeClass("active");
		$(".navbar.navbar-default ul.nav.navbar-nav > li a[href='${_pageUri}']").closest("li.navbarmenu").addClass("active");
		
		$("#main-menu").on("mouseenter", ".dropdown", function() {
			$(this).find(".firstlevel").parent().addClass("active");
			$(this).find(".firstlevel").show();
			$(this).on("mouseleave", function() {
				$(this).find(".firstlevel").hide();
				$(this).find(".firstlevel").parent().removeClass("active");
				$(".navbar.navbar-default ul.nav.navbar-nav > li a[href='${_pageUri}']").closest("li.navbarmenu").addClass("active");
			});
		});
		
		$("#main-menu").on("mouseenter", ".navbarmenu", function() {
			$(this).addClass("active");
			$(this).on("mouseleave", function() {
				$(this).removeClass("active");
				$(".navbar.navbar-default ul.nav.navbar-nav > li a[href='${_pageUri}']").closest("li.navbarmenu").addClass("active");
			});
		});
	});
</script>